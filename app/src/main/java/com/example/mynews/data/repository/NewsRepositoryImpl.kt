package com.example.mynews.data.repository

import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynews.data.db.bookmarks.BookmarkedNewsDatabaseDao
import com.example.mynews.data.entities.Article
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.ArticleDataCache
import com.example.mynews.data.entities.Status
import com.example.mynews.data.provider.LocaleProvider
import com.example.mynews.data.provider.SharedPreferencesProvider
import kotlinx.coroutines.*

class NewsRepositoryImpl(
    val newsDataSource: NewsDataSource,
    val bookmarkedNewsDatabaseDao: BookmarkedNewsDatabaseDao,
    val localeProvider: LocaleProvider,
    val sharedPreferencesProvider: SharedPreferencesProvider
): NewsRepository {


    override suspend fun getNews(type:Int?, country: String?, language: String?, category: String?, source: String?, q: String?): LiveData<List<ArticleData>> {
        return withContext(Dispatchers.IO) {
            initNewsData(type, country, language, category, source, q)
            delay(500)
            val newsList = mutableListOf<ArticleData>()
            if(getStatus().value !=Status.ERROR){
                newsDataSource.downloadedNews.value?.let {
                    it.articles.forEach{ article ->
                        var articleData = article.toData()

                        if(bookmarkedNewsDatabaseDao.getArticle(article.url) != null) {articleData.isBookmarked = true}
                        newsList.add(articleData)
                    }
                }
            }
            val newsData = MutableLiveData<List<ArticleData>>(newsList)
            return@withContext newsData
        }
    }

    override fun getStatus(): LiveData<Status>{
        return newsDataSource.apiServiceStatus
    }

    private suspend fun initNewsData(type:Int?,country: String?, language: String?, category: String?, sources: String?, q: String?){
        fetchNews(type, country, language, category = category, sources = sources, q = q)

    }

    private suspend fun fetchNews(type:Int?, country: String?, language: String?, category: String?, sources: String?, q: String?){
        if(sharedPreferencesProvider.isUsingStaticApi()) newsDataSource.fetchCache(country, category = category, sources = sources, q = q)
        else{
            if (type == 1) newsDataSource.fetchNews(country, category = category, sources = sources, q = q)
            else newsDataSource.fetchEverything(language, sources = sources, q = q)
        }
    }

    fun Article.toData(): ArticleData{
        val article = ArticleData(
            this.source,
            this.author,
            this.title,
            this.description,
            this.url,
            this.urlToImage,
            this.publishedAt,
            this.content,
            "0"
        )
        return article
    }

    companion object{
        val TOP = 1
        val EVERYTHING = 2
    }
}