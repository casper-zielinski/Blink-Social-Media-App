/*
 * AI ASSISTANCE NOTICE — coded with AI
 *
 * Motivation: we used an AI tool for this background-upload feature mainly because we
 *   wanted to see what an Android Service and BroadcastReceiver look like in a real app,
 *   and to learn from a working example.
 *
 * Why AI was needed:
 *   The upload feature needs one-time setup at app start: a notification channel (required
 *   since Android 8) and the runtime registration of the connectivity receiver. This
 *   Application-class wiring was boilerplate we did not know.
 * Written by us (the team):
 *   The decision of what has to be initialized before any screen is shown (load the queue,
 *   refresh the online state, resume any leftover uploads).
 * Supported by the AI tool:
 *   The notification-channel creation, the receiver registration, and the guarded
 *   resume-on-start logic.
 */
package com.CU.blink

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import com.CU.blink.Upload.BlinkUploadService
import com.CU.blink.Upload.ConnectivityReceiver
import com.CU.blink.Upload.NetworkStatus
import com.CU.blink.Upload.PendingUploadStore

/**
 * Application entry point. Sets up everything the upload feature needs before any screen
 * is shown: the notification channel for the upload service and the persisted upload queue.
 */
class BlinkApplication : Application() {

    private val connectivityReceiver = ConnectivityReceiver()

    override fun onCreate() {
        super.onCreate()
        createUploadNotificationChannel()
        PendingUploadStore.init(this)
        NetworkStatus.refresh(this)
        registerConnectivityReceiver()
        resumePendingUploads()
    }

    /**
     * If posts were left in the queue from a previous session (e.g. the app was closed before
     * the upload finished), pick them up again on start as long as we are online. Without this
     * a queued post would only be retried on the next connectivity change. Guarded because
     * starting a foreground service may be disallowed if the app starts in the background.
     */
    private fun resumePendingUploads() {
        if (NetworkStatus.isCurrentlyOnline(this) && PendingUploadStore.hasPending(this)) {
            runCatching { BlinkUploadService.start(this) }
        }
    }

    /**
     * Registers [ConnectivityReceiver] at runtime so it reliably receives connectivity
     * changes (manifest-declared CONNECTIVITY_CHANGE receivers are ignored on Android 7+).
     */
    @Suppress("DEPRECATION")
    private fun registerConnectivityReceiver() {
        registerReceiver(
            connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun createUploadNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                BlinkUploadService.CHANNEL_ID,
                "Uploads",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Zeigt den Fortschritt beim Hochladen von Posts"
            }
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }
}
