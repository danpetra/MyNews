package com.example.mynews.ui.everything

import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.SourceData
import com.example.mynews.data.entities.Status
import com.example.mynews.data.provider.LocaleProvider
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksRepository
import com.example.mynews.data.repository.NewsRepository
import com.example.mynews.data.repository.NewsRepositoryImpl.Companion.EVERYTHING
import com.example.mynews.data.repository.sources.SourcesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class EverythingViewModel(
    private val newsRepository: NewsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val sourcesRepository: SourcesRepository,
    localeProvider: LocaleProvider,
    private val shareProvider: ShareProvider
) : ViewModel() {

    private var _articles = MutableLiveData<List<ArticleData>>()
    val articles: LiveData<List<ArticleData>>
        get() = _articles

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _showEnterText = MutableLiveData<Boolean>()
    val showEnterText: LiveData<Boolean>
        get() = _showEnterText

    var sources: List<SourceData>? = null

    val languages = listOf("ar", "de", "en","es","fr","he","it","nl","no","pt","ru","se","ud","zh")

    val lang = localeProvider.getLang()
    var currentLang = lang
    val locale = localeProvider.getLocale()
    var currentLocale = locale
    var query: String? = null
    val apiStatus = newsRepository.getStatus()
    var currentSource:String? = null

    val everythingStatus = Transformations.map(apiStatus) {
        if (it == Status.OK && languages.indexOf(currentLang) == -1) return@map Status.NO_COUNTRY
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
                _articles.postValue(newsRepository.getNews(EVERYTHING, language = lang, q = query).value)
                _showEnterText.postValue(false)
                _status.postValue("ok, $currentLang")
                Log.i("NewsApi","Get everything in fragment, ${_status.value}")
            } catch(e: Exception){
                _articles.postValue(ArrayList())
                _showEnterText.postValue(true)
                _status.postValue("error $e")
                Log.e("NewsApi","Get everything in fragment, error $e")
            }
        }
    }

    fun getNewsForSource(){
        viewModelScope.launch(Dispatchers.IO){
            try{
                _articles.postValue(newsRepository.getNews(EVERYTHING, language = null, source = currentSource, q = query).value)
                _status.postValue("ok")
                Log.i("NewsApi","Get everything news in fragment, ${_status.value}")
            } catch(e: Exception){
                _articles.postValue(ArrayList())
                _status.postValue("error $e")
                Log.e("NewsApi","Get everything news in fragment, error $e")
            }
        }
    }

    fun getSources(): Job {
        return viewModelScope.launch(Dispatchers.IO){
            try{
                sources = sourcesRepository.getSources(locale).value
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