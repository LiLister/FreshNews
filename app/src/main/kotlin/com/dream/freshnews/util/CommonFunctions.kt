package com.dream.freshnews.util

import com.dream.freshnews.BuildConfig

/**
 * Created by lixingming on 25/03/2018.
 */

inline fun debug(code: () -> Unit) {
    if (BuildConfig.DEBUG) {
        code()
    }
}