package com.CU.blink.Upload

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Listens for network changes (CONNECTIVITY_CHANGE). When the device comes back online and
 * there are still posts waiting in [PendingUploadStore], it restarts [BlinkUploadService] so
 * failed/queued uploads are retried automatically.
 *
 * Registered at runtime in [com.CU.blink.BlinkApplication] because manifest-declared
 * CONNECTIVITY_CHANGE receivers no longer fire on modern Android.
 */
class ConnectivityReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Keep the connectivity state the UI observes up to date.
        NetworkStatus.refresh(context)

        if (NetworkStatus.isCurrentlyOnline(context) && PendingUploadStore.hasPending(context)) {
            BlinkUploadService.start(context)
        }
    }
}
