package com.dream.freshnews.data.source.remote

import com.dream.freshnews.BuildConfig
import com.dream.freshnews.FreshNewsApp
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by lixingming on 24/03/2018.
 */

class ApiCreator private constructor() {

    private val newBuilder: OkHttpClient.Builder by lazy {
        val builder = OkHttpClient.Builder()

        builder
    }

    private val okHttpClient: OkHttpClient by lazy {

        if (BuildConfig.DEBUG) {
            newBuilder.addInterceptor(OkHttpProfilerInterceptor())
        }

        newBuilder.readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .cache(Cache(FreshNewsApp.instance.applicationContext.cacheDir, 50 * 1024 * 1024))
            .build()
    }

    fun <T> createApi(clazz: Class<T>, endpoint: String): T {
        if (mapApi[clazz.name] == null) {
            val retrofit = Retrofit.Builder().client(okHttpClient)
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mapApi[clazz.name] = retrofit.create(clazz) as Any
        }

        return mapApi[clazz.name] as T
    }

    companion object {
        private var sInstance: ApiCreator? = null
        private var mapApi: HashMap<String, Any> = HashMap()

        fun instance(): ApiCreator =
            sInstance ?: synchronized(ApiCreator::class.java) {
                sInstance ?: ApiCreator().also {
                    sInstance = it
                }
            }
    }
}