package com.example.mynews.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksDataSource

class BookmarksViewModelFactory (
    private val bookmarksDataSource: BookmarksDataSource,
    private val shareProvider: ShareProvider
    ): ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BookmarksViewModel(bookmarksDataSource, shareProvider) as T
        }
}