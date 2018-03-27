package com.dream.freshnews.data.source.remote

import com.dream.freshnews.FreshNewsApp
import com.dream.freshnews.R
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


/**
 * Created by lixingming on 24/03/2018.
 */

class ApiCreator private constructor() {

    val newBuilder : OkHttpClient.Builder by lazy {
        val builder = OkHttpClient.Builder()

        builder
    }

    /**
     *  error occurred on my test phone:Android java.security.cert.CertPathValidatorException: Trust anchor for
     *  certification path not found
     *  called "addConverterFactory" to solve this issue
     */
    val okHttpClient: OkHttpClient by lazy {

        newBuilder.readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .cache(Cache(FreshNewsApp.instance.applicationContext.cacheDir, 50 * 1024 * 1024))
            .sslSocketFactory(getSSLSocketFactory(), getX509TrustManager())
            .build()
    }

    fun<T> createApi(clazz: Class<T>, endpoint: String): T {
        if (mapApi.get(clazz.name) == null) {
            val retrofit = Retrofit.Builder().client(okHttpClient)
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            mapApi.put(clazz.name, retrofit.create(clazz) as Any)
        }

        return mapApi.get(clazz.name) as T
    }

    private fun getSSLSocketFactory(): SSLSocketFactory {
        // Create a TrustManager that trusts the CAs in our KeyStore
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(getKeyStore())

        // Create an SSLContext that uses our TrustManager
        val context = SSLContext.getInstance("TLS")
        context.init(null, tmf.trustManagers, null)

        return context.socketFactory
    }

    private fun getKeyStore(): KeyStore? {
        val caInput = BufferedInputStream(
            FreshNewsApp.instance.applicationContext.resources.openRawResource(
                R.raw.newsapi_org
            )
        )

        val cf = CertificateFactory.getInstance("X.509")

        val ca: Certificate
        try {
            ca = cf.generateCertificate(caInput)
            System.out.println("ca=" + (ca as X509Certificate).subjectDN)
        } finally {
            caInput.close()
        }

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)
        return keyStore
    }

    private fun getX509TrustManager(): X509TrustManager {
        val tmf = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        // Initialise the TMF as you normally would, for example:
        try {
            tmf.init(getKeyStore())
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }

        val trustManagers = tmf.trustManagers

        val origTrustmanager = trustManagers[0] as X509TrustManager

        val result = object: X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                try {
                    origTrustmanager.checkClientTrusted(chain, authType)
                } catch(e: CertificateException) {
                    e.printStackTrace()
                }
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                try {
                    origTrustmanager.checkServerTrusted(chain, authType)
                } catch(e: CertificateException) {
                    e.printStackTrace()
                }
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return origTrustmanager.acceptedIssuers
            }
        }

        return result
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