package com.dream.freshnews.data.source

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline
import com.dream.freshnews.data.source.local.NewsLocalDataSource
import com.dream.freshnews.data.source.remote.NewsRemoteDataSource
import com.dream.freshnews.util.NetworkStateUtil

/**
 * Created by lixingming on 24/03/2018.
 */

class NewsRepository private constructor() : NewsDataSource {
    private var newsLocalDataSource: NewsLocalDataSource? = null
    private lateinit var newsRemoteDataSource: NewsRemoteDataSource

    override fun clearCachedSources() {
        newsLocalDataSource?.clearCachedSources()
    }

    override fun getSources(callback: MyCallback<List<Source>>) {
        newsLocalDataSource?.getSources({
            ok, errorMsg, data ->
            if (ok && !data.isEmpty()) {
                callback(ok, errorMsg, data)
            } else if (NetworkStateUtil.isConnected()) {
                newsRemoteDataSource.getSources({
                    rdsOk, rdsErrMsg, rdsData ->
                    // cache the sources to local data source
                    if (rdsOk && !rdsData.isEmpty()) {
                        newsLocalDataSource?.updateListSource(rdsData)
                    }

                    callback(rdsOk, rdsErrMsg, rdsData)
                })
            } else {
                callback(false, "Network connection not available", listOf())
            }
        })
    }

    override fun getTopHeadlines(params: Map<String, String>, callback: MyCallback<List<TopHeadline>>) {
        newsLocalDataSource?.getTopHeadlines(params
        ) {
                ok, errMsg, data ->
            if (ok && !data.isEmpty()) {
                callback(ok, errMsg, data)
            } else if (NetworkStateUtil.isConnected()) {
                newsRemoteDataSource.getTopHeadlines(params) { rdsOk, rdsErrMsg, rdsData ->

                    if (rdsOk && !rdsData.isEmpty()) {
                        // TODO cache the top headlines to local data source
                    }

                    callback(rdsOk, rdsErrMsg, rdsData)
                }
            } else {
                callback(false, "Network connection not available", listOf())
            }
        }
    }

    companion object {
        val KEY_SOURCE = "sources"
        val KEY_PAGE_SIZE = "pageSize"
        val KEY_PAGE = "page"

        private var mInstance: NewsRepository = NewsRepository()

        fun getInstance(localDataSource: NewsLocalDataSource, remoteDataSource: NewsRemoteDataSource): NewsRepository {
            if (mInstance::newsLocalDataSource == null) {
                mInstance.newsLocalDataSource = localDataSource
                mInstance.newsRemoteDataSource = remoteDataSource
            }

            return mInstance
        }
    }
}