package com.example.mynews.data.repository.sources

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynews.data.db.bookmarks.BookmarkedNewsDatabaseDao
import com.example.mynews.data.db.sources.SourcesDatabase
import com.example.mynews.data.db.sources.SourcesDatabaseDao
import com.example.mynews.data.entities.*
import com.example.mynews.data.provider.LocaleProvider
import kotlinx.coroutines.*


class SourcesRepositoryImpl(
    val sourcesDataSource: SourcesDataSource,
    val sourcesDatabaseDao: SourcesDatabaseDao
): SourcesRepository {


    override suspend fun getSources(country: String?, category: String?): LiveData<List<SourceData>> {

        if(sourcesDatabaseDao.getAllPlain()?.size == 0) fillDatabase().join()
        Log.i("source","${sourcesDatabaseDao.getAllPlain()?.size}" )

        country?.let { cou ->
            category?.let { cat ->
                sourcesDatabaseDao.getAllForCountryAndCategory(cou, cat)?.let { return MutableLiveData(it) }?:run{return MutableLiveData<List<SourceData>>()}
            } ?: run {
                sourcesDatabaseDao.getAllForCountry(cou)?.let { return MutableLiveData(it) }?:run{return MutableLiveData<List<SourceData>>()}
            }
        }
         ?: run{
             category?.let { cat ->
                 sourcesDatabaseDao.getAllForCategory(cat)?.let { return MutableLiveData(it) }?:run{return MutableLiveData<List<SourceData>>()}
             } ?: run {
                 sourcesDatabaseDao.getAllPlain()?.let { return MutableLiveData(it) }?:run{return MutableLiveData<List<SourceData>>()}
             }
        }
    }

    private suspend fun getFromApi(country: String?, category: String?): MutableLiveData<List<SourceData>>{
        return withContext(Dispatchers.IO) {
            val sourceList = mutableListOf<SourceData>()
            initSourcesData(country, category)
            sourcesDataSource.downloadedSources.value?.let {
                it.sources.forEach{ source ->
                    sourcesDatabaseDao.insert(source)
                    sourceList.add(source)
                }
            }
            val sourceData = MutableLiveData<List<SourceData>>(sourceList)
            return@withContext sourceData
        }
    }

    private suspend fun fillDatabase(): Job {
        return coroutineScope{launch(Dispatchers.IO) {
            initSourcesData(null, null)
            Log.i("source","Sources downloaded")
            sourcesDataSource.downloadedSources.value?.let {
                it.sources.forEach{ source ->
                    sourcesDatabaseDao.insert(source)
                }
            }
        }}
    }

    override fun getStatus(): LiveData<Status>{
        return sourcesDataSource.apiServiceSourceStatus
    }

    private suspend fun initSourcesData(country: String?, category: String?){
        fetchSources(country, category = category)
    }

    private suspend fun fetchSources(country: String?, category: String?){
        sourcesDataSource.fetchSources(country, category = category)
    }
}


