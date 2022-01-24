package com.example.mynews.data.repository

import com.example.mynews.data.entities.ArticleData

class BookmarksRepositoryImpl(
    private val bookmarksDataSource: BookmarksDataSource
) : BookmarksRepository {
    override suspend fun onBookmarkEvent(article: ArticleData) {
        if (bookmarksDataSource.getBookmark(article.url) == null){
            article.isBookmarked = true
            bookmarksDataSource.setBookmark(article)
        }
        else{
            bookmarksDataSource.deleteBookmark(article.url)
        }
    }

    override suspend fun getBookmarks(): List<ArticleData> {
        return bookmarksDataSource.getAllBookmarksPlain("0")
    }
}