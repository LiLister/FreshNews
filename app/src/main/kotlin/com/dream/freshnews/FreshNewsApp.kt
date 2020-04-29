package com.dream.freshnews

import android.app.Activity
import android.app.Application

/**
 * Created by lixingming on 25/03/2018.
 */
class FreshNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun onActivityPreDestroyed(activity: Activity): Unit {

    }

    companion object {
        lateinit var instance: FreshNewsApp
    }
}