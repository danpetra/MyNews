package com.example.mynews.ui.top

import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.example.mynews.R
import com.example.mynews.data.repository.NewsRepository
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.provider.LocaleProvider
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksDataSource
import com.example.mynews.data.repository.BookmarksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class TopViewModel(
    private val newsRepository: NewsRepository,
    private val bookmarksRepository: BookmarksRepository,
    localeProvider: LocaleProvider,
    private val shareProvider: ShareProvider
) : ViewModel() {


    val ALL_CATEGORIES = "all categories"

    private var _articles = MutableLiveData<List<ArticleData>>()
    val articles: LiveData<List<ArticleData>>
        get() = _articles

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    val categories = listOf(
        ALL_CATEGORIES, "business", "entertainment", "general", "health", "science", "sports", "technology"
    )
    var currentCategory: String? = null
    val locale = localeProvider.getLocale()
    var currentLocale = locale
    var query: String? = null
    val apiStatus = newsRepository.getStatus()

    init{
        _status.value = "permanent"
        getNews()
    }

    fun getNews(){
        viewModelScope.launch{
            try{
                 _articles.value = newsRepository.getNews(locale, currentCategory, q = query).value
                if (_articles.value == null){_status.value = "ok, null"}
                else if(_articles.value!!.isEmpty()){_status.value = "ok, empty"}

                else{_status.value = "ok, ${currentLocale}"}
            } catch(e: Exception){
                _articles.value = ArrayList()
                _status.value = "error $e"
                Log.e("NewsApi","Get news in fragment, error $e")
            }
        }
    }


    fun getShareIntent(message: String): Intent {
        return shareProvider.getShareIntent(message)
    }

    fun onBookmarkEvent(article: ArticleData){
       viewModelScope.launch(Dispatchers.IO) {
           bookmarksRepository.onBookmarkEvent(article)
        }
    }

}