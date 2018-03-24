package com.dream.freshnews.data.source.remote

import com.dream.freshnews.data.source.remote.response.SourcesResponse
import com.dream.freshnews.data.source.remote.response.TopHeadlinesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by lixingming on 24/03/2018.
 */
interface NewsApi {

    // get sources
    @GET("/v2/sources")
    fun getSources(@QueryMap params: Map<String, String>): Call<SourcesResponse>

    // get top headlines
    @GET("/v2/top-headlines")
    fun getTopHeadlines(@QueryMap params: Map<String, String>): Call<TopHeadlinesResponse>
}