package com.dream.freshnews.data.source.remote

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline
import com.dream.freshnews.data.source.MyCallback
import com.dream.freshnews.data.source.NewsDataSource
import com.dream.freshnews.data.source.remote.response.SourcesResponse
import com.dream.freshnews.data.source.remote.response.TopHeadlinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by lixingming on 24/03/2018.
 */

class NewsRemoteDataSource : NewsDataSource {

    private val KEY_API_KEY = "apiKey"
    private val API_KEY = "9b420ed5b1f8456282aefde448c51351"
    private val END_POINT = "https://newsapi.org"

    private lateinit var newsApi: NewsApi

    init {
        newsApi = ApiCreator.instance().createApi(NewsApi::class.java, END_POINT)
    }

    override fun clearCachedSources() {
        // Do nothing
    }

    override fun getSources(callback: MyCallback<List<Source>>) {
        val params = mapOf(KEY_API_KEY to API_KEY)
        newsApi.getSources(params).enqueue(object : Callback<SourcesResponse> {
            override fun onFailure(call: Call<SourcesResponse>?, t: Throwable?) {
                val errorMsg = t?.localizedMessage ?: "failed to get sources"
                callback(false, errorMsg, listOf())
            }

            override fun onResponse(call: Call<SourcesResponse>?, response: Response<SourcesResponse>?) {
                val result = response?.body()?.sources ?: listOf()

                callback(true, null, result)
            }
        })
    }

    override fun getTopHeadlines(params: Map<String, String>, callback: MyCallback<List<TopHeadline>>) {
        // TODO check whether there more articles available

        val parameters = mutableMapOf<String, String>()
        parameters.putAll(params)
        parameters.put(KEY_API_KEY, API_KEY)

        newsApi.getTopHeadlines(parameters).enqueue(object : Callback<TopHeadlinesResponse> {
            override fun onFailure(call: Call<TopHeadlinesResponse>?, t: Throwable?) {
                val errorMsg = t?.localizedMessage ?: "failed to get sources"
                callback(false, errorMsg, listOf())
            }

            override fun onResponse(call: Call<TopHeadlinesResponse>?, response: Response<TopHeadlinesResponse>?) {
                val result = response?.body()?.articles ?: listOf()

                callback(true, null, result)
            }
        })
    }
}