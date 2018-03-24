package com.dream.freshnews.data.source

import com.dream.freshnews.data.Source
import com.dream.freshnews.data.TopHeadline
import com.dream.freshnews.data.source.NewsRepository.Companion.mInstance
import com.dream.freshnews.data.source.local.NewsLocalDataSource
import com.dream.freshnews.data.source.remote.NewsRemoteDataSource

/**
 * Created by lixingming on 24/03/2018.
 */

class NewsRepository private constructor(): NewsDataSource {
    private lateinit var newsLocalDataSource: NewsLocalDataSource
    private lateinit var newsRemoteDataSource: NewsRemoteDataSource

    override fun getSources(callback: (data: List<Source>) -> Unit) {
        newsLocalDataSource.getSources({
            if (it.size == 0) {
                newsRemoteDataSource.getSources({
                    // TODO cache the sources to local data source

                    callback(it)
                })
            }
        })
    }

    override fun getTopHeadlines(params: Map<String, String>, callback: (data: List<TopHeadline>) -> Unit) {
        newsLocalDataSource.getTopHeadlines(params, {
            newsRemoteDataSource.getTopHeadlines(params, {
                // TODO cache the top headlines to local data source

                callback(it)
            })
        })
    }

    companion object {
        private var mInstance: NewsRepository = NewsRepository()

        fun getInstance(localDataSource: NewsLocalDataSource, remoteDataSource: NewsRemoteDataSource): NewsRepository {
            if (!mInstance::newsLocalDataSource.isInitialized) {
                mInstance.newsLocalDataSource = localDataSource
                mInstance.newsRemoteDataSource = remoteDataSource
            }

            return mInstance
        }

    }

}