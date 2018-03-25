package com.dream.freshnews.util

import com.dream.freshnews.data.source.NewsDataSource
import com.dream.freshnews.data.source.NewsRepository
import com.dream.freshnews.data.source.local.NewsLocalDataSource
import com.dream.freshnews.data.source.remote.NewsRemoteDataSource

/**
 * Created by lixingming on 25/03/2018.
 */
object MyInjectionUtil {
    val newsRepository: NewsDataSource by lazy {
        NewsRepository.getInstance(NewsLocalDataSource(), NewsRemoteDataSource())
    }

}