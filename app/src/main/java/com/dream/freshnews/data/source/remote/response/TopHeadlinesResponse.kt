package com.dream.freshnews.data.source.remote.response

import com.dream.freshnews.data.TopHeadline

/**
 * Created by lixingming on 24/03/2018.
 */
data class TopHeadlinesResponse(val status: String, val totalResults: Int, val articles: List<TopHeadline>) {
}