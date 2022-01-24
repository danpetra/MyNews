package com.example.mynews.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynews.data.entities.ArticleResponce
import com.example.mynews.data.entities.Status
import com.example.mynews.data.network.NewsApiService
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


    override suspend fun fetchNews(country: String, category: String?, sources: String?, q:String?) {
        try{
            val fetchedNews = apiService.getTopHeadlines(country = country, category = category, sources = sources, q = q)

            _downloadedNews.postValue(fetchedNews)
            _apiServiceStatus.postValue(Status.OK)
            Log.i("fetch","Fetch no error, q $q")
        } catch (error: IOException)
        {
            Log.e("fetch","Fetch error ${ error.message}")
            _apiServiceStatus.postValue(Status.ERROR)
        }
    }
}