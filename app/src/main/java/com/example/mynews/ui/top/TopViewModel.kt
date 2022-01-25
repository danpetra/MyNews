package com.example.mynews.ui.top

import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.example.mynews.R
import com.example.mynews.data.repository.NewsRepository
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Status
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

    private val _userStatus = MutableLiveData<String>()
    val userStatus: LiveData<String>
        get() = _userStatus

    private val _wholeStatus = MutableLiveData<String>()
    val wholeStatus: LiveData<String>
        get() = _wholeStatus


    val countries = listOf("ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de",
        "eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my",
        "ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa",
        "se","sg","si","sk","th","tr","tw","ua","us","ve","za")

    val categories = listOf(
        ALL_CATEGORIES, "business", "entertainment", "general", "health", "science", "sports", "technology"
    )
    var currentCategory: String? = null
    val locale = localeProvider.getLocale()
    var currentLocale = locale
    var query: String? = null
    val apiStatus = newsRepository.getStatus()


    val localeStatus = Transformations.map(apiStatus) {
        if (it == Status.OK && countries.indexOf(currentLocale) == -1) return@map Status.NO_COUNTRY
        else return@map Status.OK
    }

    init{
        _status.value = "init"
        getNews()
    }

    fun getNews(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                 _articles.postValue(newsRepository.getNews(locale, currentCategory, q = query).value)
                if (_articles.value == null){_status.postValue("ok, null")}
                else if(_articles.value!!.isEmpty()){_status.postValue("ok, empty")}

                _status.postValue("ok, ${currentLocale}")
            } catch(e: Exception){
                _articles.postValue(ArrayList())
                _status.postValue("error $e")
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