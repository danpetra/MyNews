package com.example.mynews.data.repository

import com.example.mynews.data.entities.ArticleData

interface BookmarksRepository {
    suspend fun onBookmarkEvent(article: ArticleData)
    suspend fun getBookmarks():List<ArticleData>
}