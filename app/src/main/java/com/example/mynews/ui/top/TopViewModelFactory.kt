package com.example.mynews.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynews.data.repository.NewsRepository
import com.example.mynews.data.provider.LocaleProvider
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksDataSource
import com.example.mynews.data.repository.BookmarksRepository

class TopViewModelFactory(
    private val newsRepository: NewsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val localeProvider: LocaleProvider,
    private val shareProvider: ShareProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopViewModel(newsRepository, bookmarksRepository, localeProvider, shareProvider) as T
    }
}