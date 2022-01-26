package com.example.mynews.data.repository.sources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynews.data.entities.ArticleResponce
import com.example.mynews.data.entities.SourceResponce
import com.example.mynews.data.entities.Status
import com.example.mynews.data.network.NewsApiService
import java.io.IOException

class SourcesDataSourceImpl(
    private val apiService: NewsApiService
) : SourcesDataSource {
    private val _downloadedSources = MutableLiveData<SourceResponce>()
    override val downloadedSources: LiveData<SourceResponce>
    get() = _downloadedSources

    private val _apiServiceSourceStatus = MutableLiveData<Status>(Status.LOADING)
    override val apiServiceSourceStatus: LiveData<Status>
        get() = _apiServiceSourceStatus


    override suspend fun fetchSources(country: String?, category: String?) {
        try{
            val fetchedSources = apiService.getSources(country = country)
            _downloadedSources.postValue(fetchedSources)
            _apiServiceSourceStatus.postValue(Status.OK)
        } catch (error: IOException)
        {
            _apiServiceSourceStatus.postValue(Status.ERROR)
        }
    }
}
