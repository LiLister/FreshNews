package com.dream.freshnews.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import com.dream.freshnews.FreshNewsApp

/**
 * Created by lixingming on 25/03/2018.
 * Modified on April 29 2020
 * Some of the code referenced to: https://medium
 * .com/ki-labs-engineering/monitoring-wifi-connectivity-status-part-2-7c78f3afd916
 */
object NetworkStateUtil {

    private var connected: Boolean = false

    private var networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            connected = false
        }
        override fun onUnavailable() {
            connected = false
        }
        override fun onLosing(network: Network?, maxMsToLive: Int) {
            connected = false
        }
        override fun onAvailable(network: Network?) {
            connected = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun init() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val connectivityManager = FreshNewsApp.instance.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun isConnected(): Boolean {
        return this.connected
    }
}