package com.example.mynews.ui.bookmarks

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.provider.ShareProvider
import com.example.mynews.data.repository.BookmarksDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class BookmarksViewModel(
    private val bookmarksDataSource: BookmarksDataSource,
    private val shareProvider: ShareProvider
) : ViewModel() {

    private var _articles = MutableLiveData<List<ArticleData>>()
    val articles: LiveData<List<ArticleData>>
        get() = _articles

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    init{
        _status.value = "init"
        getBookmarks()
    }

    fun getBookmarks(){

        viewModelScope.launch(Dispatchers.IO) {
            try{
                _articles.postValue(bookmarksDataSource.getAllBookmarksPlain("0"))
                if (_articles.value == null){
                    _status.postValue("ok, null")}
                else if(_articles.value!!.isEmpty()){_status.postValue("ok, empty")}
                else{_status.postValue("ok")}
            } catch(e: Exception){
                _articles.postValue(ArrayList())
                _status.postValue("error $e")
                Log.e("db","Get bookmarks in fragment, error ${e.message}")
            }
        }
    }

    fun deleteBookmark(key: String){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                bookmarksDataSource.deleteBookmark(key)
                Log.i("db","Delete bookmarks in fragment")
            } catch(e: Exception){
                Log.i("db","Delete bookmarks in fragment, error ${e.message}")
            }
        }
    }

    fun getShareIntent(message: String): Intent {
        return shareProvider.getShareIntent(message)
    }

}