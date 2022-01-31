package com.example.mynews.ui.top

import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.example.mynews.R
import com.example.mynews.data.repository.NewsRepository
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.SourceData
import com.example.mynews.data.entities.Status
import com.example.mynews.data.provider.LocaleProvider
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksDataSource
import com.example.mynews.data.repository.BookmarksRepository
import com.example.mynews.data.repository.NewsRepositoryImpl.Companion.TOP
import com.example.mynews.data.repository.sources.SourcesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.http.HTTP
import java.lang.Exception

class TopViewModel(
    private val newsRepository: NewsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val sourcesRepository: SourcesRepository,
    localeProvider: LocaleProvider,
    private val shareProvider: ShareProvider
) : ViewModel() {


    val ALL_CATEGORIES = "all categories"

    private var _articles = MutableLiveData<List<ArticleData>?>()
    val articles: LiveData<List<ArticleData>?>
        get() = _articles

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _userStatus = MutableLiveData<String>()
    val userStatus: LiveData<String>
        get() = _userStatus


    var sources: List<SourceData>? = null

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

    var currentSource:String? = null


    val localeStatus = Transformations.map(apiStatus) {
        if (it == Status.OK && countries.indexOf(currentLocale) == -1) return@map Status.NO_COUNTRY
        else return@map Status.OK
    }

    init{
        _status.value = "init"
        getNews()
        getSources()
    }

    fun update(){
        currentSource?.run { getNewsForSource() }
            ?: run { getNews() }
    }

    fun getNews(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                _articles.postValue(newsRepository.getNews(TOP, country = locale, category = currentCategory, q = query).value)
                _status.postValue("ok, $currentLocale")
                Log.i("NewsApi","Get news in fragment, ${_status.value}")
            } catch(e: Exception){
                _articles.postValue(ArrayList())
                _status.postValue("error $e")
                Log.e("NewsApi","Get news in fragment, error $e")
            }
        }
    }

    fun getNewsForSource(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                _articles.postValue(newsRepository.getNews(TOP, country = null, source = currentSource, q = query).value)
                _status.postValue("ok")
                Log.i("NewsApi","Get news in fragment, ${_status.value}")
            } catch(e: Exception){
                //_articles.postValue(ArrayList())
                _status.postValue("error $e")
                Log.e("NewsApi","Get news in fragment, error $e")
            }
        }
    }

    fun getSources(): Job {
        return viewModelScope.launch(Dispatchers.IO){
            try{
                sources = sourcesRepository.getSources(locale, currentCategory).value
            } catch(e: Exception){
                Log.e("source","Get sources in fragment, error $e")
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