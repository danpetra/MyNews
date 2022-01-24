package com.example.mynews.data.repository

import androidx.lifecycle.LiveData
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Status

interface BookmarksDataSource {
    suspend fun getAllBookmarks(userId: String): LiveData<List<ArticleData>>
    suspend fun getBookmark(key: String): ArticleData?
    suspend fun getAllBookmarksPlain(userId: String): List<ArticleData>
    suspend fun setBookmark(article: ArticleData)
    suspend fun deleteBookmark(key: String)
    suspend fun clearBookmarks()
    fun getStatus(): LiveData<Status>
}