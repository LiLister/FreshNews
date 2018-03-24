package com.dream.freshnews.data.source.local

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline
import com.dream.freshnews.data.source.NewsDataSource

/**
 * Created by lixingming on 24/03/2018.
 */

class NewsLocalDataSource: NewsDataSource {

    override fun getSources(callback: (data: List<Source>) -> Unit) {
        // TODO get sources from cache

        callback(listOf())
    }

    override fun getTopHeadlines(params: Map<String, String>, callback: (data: List<TopHeadline>) -> Unit) {
        // TODO get top headlines from cache

       callback(listOf())
    }
}