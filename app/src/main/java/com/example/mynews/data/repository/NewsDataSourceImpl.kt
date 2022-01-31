package com.example.mynews.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynews.data.entities.ArticleResponce
import com.example.mynews.data.entities.Status
import com.example.mynews.data.network.NewsApiService
import com.example.mynews.data.network.STATIC_JSON_RESPONSE
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.IOException

class NewsDataSourceImpl(
    private val apiService: NewsApiService
) : NewsDataSource {
    private val _downloadedNews = MutableLiveData<ArticleResponce>()
    override val downloadedNews: LiveData<ArticleResponce>
    get() = _downloadedNews

    private val _apiServiceStatus = MutableLiveData<Status>(Status.LOADING)
    override val apiServiceStatus: LiveData<Status>
        get() = _apiServiceStatus


    override suspend fun fetchNews(country: String?, category: String?, sources: String?, q:String?) {
        try{
            val fetchedNews = apiService.getTopHeadlines(country = country, category = category, sources = sources, q = q)

            _downloadedNews.postValue(fetchedNews)
            _apiServiceStatus.postValue(Status.OK)
            Log.i("NewsApi","Fetch no error, q $q")
        } catch (error: IOException)
        {
            Log.e("NewsApi","Fetch error ${ error.message}")
            _apiServiceStatus.postValue(Status.ERROR)
        }
    }

    override suspend fun fetchCache(country: String?, category: String?, sources: String?, q:String?) {

        val jsonText = STATIC_JSON_RESPONSE

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<ArticleResponce> = moshi.adapter(ArticleResponce::class.java)
        jsonText?.let {
            val fetchedNews = jsonAdapter.fromJson(jsonText)
            fetchedNews?.let { news ->
                _downloadedNews.postValue(news)
                _apiServiceStatus.postValue(Status.OK)
            }
        }

    }

    override suspend fun fetchEverything(language: String?, sources: String?, q:String?) {
        try{
            val fetchedNews = apiService.getEverything(language = language, sources = sources, q = q)

            _downloadedNews.postValue(fetchedNews)
            _apiServiceStatus.postValue(Status.OK)
            Log.i("NewsApi","Fetch everything no error, q $q")
        } catch (error: IOException)
        {
            Log.e("NewsApi","Fetch everything error ${ error.message}")
            _apiServiceStatus.postValue(Status.ERROR)
        }
    }

}