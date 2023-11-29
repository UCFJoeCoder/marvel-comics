package com.ucfjoe.marvelcomics.feature_marvel.data.remote

import com.ucfjoe.marvelcomics.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.jvm.Throws

class NetworkInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val ts = System.currentTimeMillis().toString()
        val apiKey = BuildConfig.MARVEL_API_KEY
        val hash = "$ts${BuildConfig.MARVEL_PRIVATE_KEY}$apiKey"

        val httpUrl = originalRequest.url.newBuilder()
            .addQueryParameter(TS, ts)
            .addQueryParameter(API_KEY, apiKey)
            .addQueryParameter(HASH, md5(hash))
            .build()

        val builder = originalRequest.newBuilder().url(httpUrl)
        val newRequest = builder.build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val TS = "ts"
        private const val API_KEY = "apikey"
        private const val HASH = "hash"
    }
}

private fun md5(string: String) : String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(string.toByteArray())).toString(16).padStart(32, '0')
}

