package com.dream.freshnews

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.dream.freshnews.util.NetworkStateUtil

/**
 * Created by lixingming on 25/03/2018.
 */
class FreshNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        NetworkStateUtil.init()
    }

    companion object {
        lateinit var instance: FreshNewsApp
    }
}