package com.example.mynews.data.repository.sources


import androidx.lifecycle.LiveData
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.SourceData
import com.example.mynews.data.entities.SourceResponce
import com.example.mynews.data.entities.Status

interface SourcesRepository {
    suspend fun getSources(country: String?, category: String? = null): LiveData<List<SourceData>>
    fun getStatus(): LiveData<Status>
}
