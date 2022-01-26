package com.example.mynews.data.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.ArticleResponce
import com.example.mynews.data.entities.SourceResponce
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import retrofit2.converter.gson.GsonConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Response

val API_KEY = "824bee49e440427989656cf5a21d9ded"
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

    @GET("top-headlines")
    suspend fun getTopHeadlinesForCategory(
        @Query("apiKey") apiKey: String  = API_KEY,
        @Query("category") category: String,
        @Query("country") country: String = Locale.getDefault().country
    ): ArticleResponce

    @GET("top-headlines")
    suspend fun getTopHeadlinesForSources(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String,
        @Query("country") country: String = Locale.getDefault().country
    ): ArticleResponce

    @GET("top-headlines")
    suspend fun getTopHeadlinesForSearch(
        @Query("apiKey") apiKey: String  = API_KEY,
        @Query("q") query: String,
        @Query("country") country: String = Locale.getDefault().country
    ): ArticleResponce

    @GET("top-headlines")
    suspend fun getTopHeadlinesForCategoryForSearch(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String,
        @Query("category") category: String,
        @Query("country") country: String = Locale.getDefault().country
    ): ArticleResponce

    @GET("everything")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") country: String = java.util.Locale.getDefault().country
    ): ArticleResponce

    @GET("sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") country: String? = null,
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


/*object NewsApi{
    val retrofitService: NewsApiService by lazy{
        retrofit.create(NewsApiService::class.java)
    }
}*/
