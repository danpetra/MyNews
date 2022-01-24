package com.example.mynews.data.repository

import androidx.lifecycle.LiveData
import com.example.mynews.data.entities.ArticleResponce
import com.example.mynews.data.entities.Status

interface NewsDataSource {
    val downloadedNews: LiveData<ArticleResponce>
    val apiServiceStatus: LiveData<Status>
    suspend fun fetchNews(country: String, category: String? = null, sources: String? = null, q: String? = null)
}