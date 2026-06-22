/*
 * AI ASSISTANCE NOTICE — coded with AI
 *
 * Motivation: we used an AI tool for this background-upload feature mainly because we
 *   wanted to see what an Android Service and BroadcastReceiver look like in a real app,
 *   and to learn from a working example.
 *
 * Why AI was needed:
 *   Knowing that a CONNECTIVITY_CHANGE BroadcastReceiver has to be registered at runtime
 *   (manifest-declared ones are ignored on Android 7+) is a non-obvious platform detail.
 * Written by us (the team):
 *   The retry idea — when the device is back online and posts are still queued, restart
 *   the upload service so the queued posts are sent automatically.
 * Supported by the AI tool:
 *   The BroadcastReceiver wiring and the runtime-registration note.
 */
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
