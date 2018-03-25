package com.dream.freshnews

import android.app.Application

/**
 * Created by lixingming on 25/03/2018.
 */
class FreshNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: FreshNewsApp
    }
}