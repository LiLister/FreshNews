package com.dream.freshnews.data.source.remote

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline
import com.dream.freshnews.data.source.NewsDataSource
import com.dream.freshnews.data.source.remote.response.SourcesResponse
import com.dream.freshnews.data.source.remote.response.TopHeadlinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by lixingming on 24/03/2018.
 */

class NewsRemoteDataSource: NewsDataSource {

    private val API_KEY = "9b420ed5b1f8456282aefde448c51351"
    private val END_POINT = "https://newsapi.org"

    private lateinit var newsApi: NewsApi

    init {
        newsApi = ApiCreator.instance().createApi(NewsApi::class.java, END_POINT)
    }

    override fun getSources(callback: (data: List<Source>) -> Unit) {
        val params = mapOf("apiKey" to API_KEY)
        newsApi.getSources(params).enqueue(object: Callback<SourcesResponse> {
            override fun onFailure(call: Call<SourcesResponse>?, t: Throwable?) {
                callback(listOf())
            }

            override fun onResponse(call: Call<SourcesResponse>?, response: Response<SourcesResponse>?) {
                val result = response?.body()?.sources ?: listOf()

                callback(result)
            }
        })
    }

    override fun getTopHeadlines(params: Map<String, String>, callback: (data: List<TopHeadline>) -> Unit) {
        // TODO check whether there more articles available

        val parameters = mutableMapOf<String, String>("apiKey" to API_KEY)
        parameters.putAll(params)

        newsApi.getTopHeadlines(params).enqueue(object: Callback<TopHeadlinesResponse> {
            override fun onFailure(call: Call<TopHeadlinesResponse>?, t: Throwable?) {
                callback(listOf())
            }

            override fun onResponse(call: Call<TopHeadlinesResponse>?, response: Response<TopHeadlinesResponse>?) {
                val result = response?.body()?.articles ?: listOf()

                callback(result)
            }
        })
    }
}