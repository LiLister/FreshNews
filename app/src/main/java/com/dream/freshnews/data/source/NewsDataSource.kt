package com.dream.freshnews.data.source

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline

/**
 * Created by lixingming on 24/03/2018.
 */

typealias MyCallback<T> = (ok: Boolean, errorMsg: String?, data: T) -> Unit

interface NewsDataSource {
    fun clearCachedSources()
    fun getSources(callback: MyCallback<List<Source>>)
    fun getTopHeadlines(params: Map<String, String>, callback: MyCallback<List<TopHeadline>>)
}

