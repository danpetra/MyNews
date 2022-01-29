package com.example.mynews.ui.everything

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynews.data.repository.NewsRepository
import com.example.mynews.data.provider.LocaleProvider
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksRepository
import com.example.mynews.data.repository.sources.SourcesRepository

class EverythingViewModelFactory(
    private val newsRepository: NewsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val sourcesRepository: SourcesRepository,
    private val localeProvider: LocaleProvider,
    private val shareProvider: ShareProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EverythingViewModel(newsRepository, bookmarksRepository, sourcesRepository, localeProvider, shareProvider) as T
    }
}