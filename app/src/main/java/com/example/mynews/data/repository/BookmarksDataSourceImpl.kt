package com.example.mynews.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.mynews.data.db.bookmarks.BookmarkedNewsDatabaseDao
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Status

class BookmarksDataSourceImpl(
    val bookmarkedNewsDatabaseDao: BookmarkedNewsDatabaseDao
) : BookmarksDataSource {
    override suspend fun getAllBookmarks(userId: String): LiveData<List<ArticleData>> {
        return bookmarkedNewsDatabaseDao.getAll()
    }

    override suspend fun getBookmark(key: String): ArticleData? {
        return bookmarkedNewsDatabaseDao.getArticle(key)
    }

    override suspend fun getAllBookmarksPlain(userId: String): List<ArticleData> {
        return bookmarkedNewsDatabaseDao.getAllPlain()
    }

    override suspend fun setBookmark(article: ArticleData) {
        bookmarkedNewsDatabaseDao.insert(article)
    }

    override suspend fun deleteBookmark(key: String) {
        bookmarkedNewsDatabaseDao.deleteArticle(key)
    }

    override suspend fun clearBookmarks() {
        bookmarkedNewsDatabaseDao.clear()
    }


}