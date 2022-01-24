package com.example.mynews.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksRepository
import com.example.mynews.ui.bookmarks.BookmarksViewModel
import com.example.news.article.ArticleViewModel

class ArticleViewModelFactory (
    private val bookmarksRepository: BookmarksRepository,
    private val shareProvider: ShareProvider
    ): ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ArticleViewModel(bookmarksRepository, shareProvider) as T
        }
}


