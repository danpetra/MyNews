package com.example.mynews.data.repository.sources


import androidx.lifecycle.LiveData
import com.example.mynews.data.entities.SourceResponce
import com.example.mynews.data.entities.Status

interface SourcesDataSource {
    val downloadedSources: LiveData<SourceResponce>
    val apiServiceSourceStatus: LiveData<Status>
    suspend fun fetchSources(country: String?, category: String? = null)
}
