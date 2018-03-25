package com.dream.freshnews.data.source.local

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline
import com.dream.freshnews.data.source.MyCallback
import com.dream.freshnews.data.source.NewsDataSource
import com.dream.freshnews.util.PreferencesUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by lixingming on 24/03/2018.
 */

class NewsLocalDataSource: NewsDataSource {

    private var listSource = mutableListOf<Source>()

    init {
        val jsonSources = PreferencesUtil.getSources()
        if (jsonSources != null) {
            try {
                val type = object : TypeToken<List<Source>>() {}.type
                val gson = Gson()
                val jsonArray = gson.fromJson(jsonSources, type) as List<Source>
                listSource.addAll(jsonArray)
            } catch (e: Exception) {
                // Just ignore the exception. it might be caused by Source changed
            }
        }
    }

    fun updateListSource(sources: List<Source>) {
        listSource.clear()
        listSource.addAll(sources)

        val jsonSources = Gson().toJson(listSource)
        PreferencesUtil.putSources(jsonSources)
    }

    override fun getSources(callback: MyCallback<List<Source>>) {
        callback(true, null, listSource)
    }

    override fun getTopHeadlines(params: Map<String, String>, callback: MyCallback<List<TopHeadline>>) {
        // TODO get top headlines from cache

       callback(false, null, listOf())
    }
}