package com.dicoding.abednego.mendaurid.data.api.retrofit

import com.dicoding.abednego.mendaurid.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MendaurApiConfig {

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun getMendaurApiService(): MendaurApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mendaur-app-c5tvvxbrdq-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
        return retrofit.create(MendaurApiService::class.java)
    }
}