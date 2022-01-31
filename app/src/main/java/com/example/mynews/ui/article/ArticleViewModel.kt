package com.example.mynews.ui.article

import android.content.Intent
import androidx.lifecycle.ViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val bookmarksRepository: BookmarksRepository,
    private val shareProvider: ShareProvider
) : ViewModel() {

    fun getShareIntent(message: String) : Intent = shareProvider.getShareIntent(message)

    fun onBookmarkEvent(article: ArticleData){
        viewModelScope.launch(Dispatchers.IO) {
            bookmarksRepository.onBookmarkEvent(article)
        }
    }
}