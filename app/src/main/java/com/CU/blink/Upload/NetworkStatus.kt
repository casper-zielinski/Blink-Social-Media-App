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
