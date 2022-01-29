package com.example.mynews.data.repository

import androidx.lifecycle.LiveData
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Status

interface NewsRepository {
    suspend fun getNews(type:Int?, country: String? = null, language: String? = null, category: String? = null, source: String? = null, q: String? = null): LiveData<List<ArticleData>>
    fun getStatus(): LiveData<Status>
}