package com.dream.freshnews.util

import android.content.Context
import android.net.ConnectivityManager
import com.dream.freshnews.FreshNewsApp

/**
 * Created by lixingming on 25/03/2018.
 */
object NetworkStateUtil {
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