package com.dream.freshnews.data.source

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline

/**
 * Created by lixingming on 24/03/2018.
 */

interface NewsDataSource {
    fun getSources(callback: (data: List<Source>) -> Unit): Unit
    fun getTopHeadlines(params: Map<String, String>, callback: (data: List<TopHeadline>) -> Unit): Unit
}