package com.dream.freshnews.util

import android.content.Context
import android.content.SharedPreferences
import com.dream.freshnews.FreshNewsApp
import java.util.*

/**
 * Created by lixingming on 25/03/2018.
 */
object PreferencesUtil {
    private val cachedData = HashMap<String, String>()

    private var sp: SharedPreferences = FreshNewsApp.instance.applicationContext
        .getSharedPreferences("FreshNews", Context.MODE_PRIVATE)

    private val KEY_SOURCES = "sources"

    fun getSources(): String? {
        return getString(KEY_SOURCES)
    }

    fun putSources(josnSources: String) {
        putString(KEY_SOURCES, josnSources)
    }

    fun clearSources() {
        putString(KEY_SOURCES, null, true)
    }

    private fun putString(key: String, value: String?, sync: Boolean = false) {
        if (sync) {
            sp.edit().putString(key, value).commit()
        } else {
            sp.edit().putString(key, value).apply()
        }

        updateCache(key, value)
    }

    private fun getString(key: String, defValue: String? = null): String? {
        return sp.getString(key, defValue)
    }

    private fun putBoolean(key: String, value: Boolean, sync: Boolean = false) {
        if (sync) {
            sp.edit().putBoolean(key, value).commit()
        } else {
            sp.edit().putBoolean(key, value).apply()
        }
    }

    private fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defValue)
    }

    private fun updateCache(key: String, value: String?) {
        if (value == null) {
            cachedData.remove(key)
        } else {
            cachedData.put(key, value)
        }
    }

}