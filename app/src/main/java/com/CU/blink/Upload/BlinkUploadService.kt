package com.CU.blink.Upload

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import com.CU.blink.HomePage.Post
import com.CU.blink.R
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Foreground service that uploads queued posts to Firestore.
 *
 * While running it shows an ongoing "Post wird hochgeladen…" notification so the upload
 * keeps going even if the user leaves the app. It works through [PendingUploadStore] one
 * post at a time and stops itself once the queue is empty. If it can't finish (offline or
 * a failed write) the posts stay in the queue and [ConnectivityReceiver] retries later.
 */
class BlinkUploadService : Service() {

    private val db = FirebaseFirestore.getInstance()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        } else {
            0
        }
        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            buildNotification("Post wird hochgeladen…"),
            type
        )
        // The Cloudinary upload blocks, so the queue is processed on a worker thread.
        Thread { processQueue() }.start()
        // If the system kills us mid-upload, ConnectivityReceiver / next post will restart us.
        return START_NOT_STICKY
    }

    /**
     * Uploads queued posts one by one until the queue is empty or we hit a problem
     * (offline, image upload failed, Firestore write failed). On a problem the post stays
     * queued so [ConnectivityReceiver] can retry it once we are online again.
     */
    private fun processQueue() {
        while (true) {
            val next = PendingUploadStore.peek(this) ?: break
            if (!NetworkStatus.isCurrentlyOnline(this)) break

            // 1. If the post has an image, upload it to Cloudinary first.
            var imageUrl = ""
            if (!next.imageUri.isNullOrEmpty()) {
                val url = CloudinaryUploader.upload(this, Uri.parse(next.imageUri))
                    ?: break // image upload failed: keep it queued and retry later
                imageUrl = url
            }

            // 2. Write the post (now with its hosted image URL) to Firestore.
            val post = Post(
                content = next.content,
                name = next.name,
                username = next.username,
                imageUrl = imageUrl
            )
            try {
                Tasks.await(db.collection("Posts").add(post))
                PendingUploadStore.remove(this, next.id)
                // The image was copied into our files dir on enqueue; clean it up now.
                next.imageUri?.let { runCatching { Uri.parse(it).path?.let { p -> java.io.File(p).delete() } } }
            } catch (e: Exception) {
                break // keep the post queued so it is retried once we are online again
            }
        }
        finish()
    }

    private fun finish() {
        // Service lifecycle calls are posted to the main thread to be safe.
        Handler(Looper.getMainLooper()).post {
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }
    }

    private fun buildNotification(text: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Blink")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    companion object {
        const val CHANNEL_ID = "blink_upload_channel"
        const val NOTIFICATION_ID = 1001

        /** Starts the service (as a foreground service) to process the upload queue. */
        fun start(context: Context) {
            val intent = Intent(context, BlinkUploadService::class.java)
            ContextCompat.startForegroundService(context, intent)
        }
    }
}
