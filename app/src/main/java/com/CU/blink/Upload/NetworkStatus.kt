/*
 * AI ASSISTANCE NOTICE — coded with AI
 *
 * Motivation: we used an AI tool for this background-upload feature mainly because we
 *   wanted to see what an Android Service and BroadcastReceiver look like in a real app,
 *   and to learn from a working example.
 *
 * Why AI was needed:
 *   Checking for a real internet connection uses the ConnectivityManager /
 *   NetworkCapabilities API, whose exact calls we did not know.
 * Written by us (the team):
 *   The idea to expose a single "are we online?" state that both the receiver and the UI
 *   can read (to show the offline hint).
 * Supported by the AI tool:
 *   The capability-check implementation and exposing it as a StateFlow.
 */
package com.CU.blink.Upload

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Tracks whether the device currently has a usable internet connection.
 *
 * [isOnline] is kept up to date by [ConnectivityReceiver] and can be observed by the UI
 * to show an offline hint. [isCurrentlyOnline] does a fresh, synchronous check.
 */
object NetworkStatus {
    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    fun isCurrentlyOnline(context: Context): Boolean {
        val cm = context.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /** Re-reads the real connection state and publishes it to [isOnline]. */
    fun refresh(context: Context) {
        _isOnline.value = isCurrentlyOnline(context)
    }
}
