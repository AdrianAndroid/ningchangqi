package com.dongnaoedu.jetpackpaging.net

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
object RetrofitClient {

    private val instance: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            //Log.d("ning", it)
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
            .baseUrl("http://192.168.1.4:8080/pagingserver_war/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createApi(clazz: Class<T>): T {
        return instance.create(clazz) as T
    }
}