package com.example.mynews.data.network

import com.example.mynews.data.entities.ArticleResponce
import com.example.mynews.data.entities.SourceResponce
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

val API_KEY = "7da15afd85a443ab8a7e06ce2778bcc5"//"824bee49e440427989656cf5a21d9ded"
private val BASE_URL = "https://newsapi.org/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String  = API_KEY,
        @Query("country") country: String?,
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("q") q: String? = null
    ): ArticleResponce

    @GET("everything")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("language") language: String?,
        @Query("sources") sources: String? = null,
        @Query("q") q: String? = null
    ): ArticleResponce

    @GET("sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") country: String? = null,
        @Query("language") language: String? = null
    ): SourceResponce

    companion object{
        operator fun invoke(): NewsApiService{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .baseUrl(BASE_URL)
                .build()
                .create(NewsApiService::class.java)
            return retrofit
        }
    }
}
