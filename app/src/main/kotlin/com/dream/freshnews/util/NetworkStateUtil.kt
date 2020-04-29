package com.dream.freshnews.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import com.dream.freshnews.FreshNewsApp

/**
 * Created by lixingming on 25/03/2018.
 */
object NetworkStateUtil {


//    fun init() {
//        val mConnectivityManager = FreshNewsApp.instance.applicationContext.getSystemService(
//            Context.CONNECTIVITY_SERVICE
//        ) as ConnectivityManager
//        mConnectivityManager.registerDefaultNetworkCallback(object: ConnectivityManager
//        .NetworkCallback {
//            override fun onAvailable(network: Network) {
//                super.onAvailable(network)
//            }
//        })
//    }

    fun isConnected(): Boolean {
        val mConnectivityManager = FreshNewsApp.instance.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable
        }

        return false
    }
}