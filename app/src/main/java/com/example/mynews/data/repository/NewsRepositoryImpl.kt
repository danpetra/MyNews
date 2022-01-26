package com.example.mynews.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mynews.data.db.bookmarks.BookmarkedNewsDatabaseDao
import com.example.mynews.data.entities.Article
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.Status
import com.example.mynews.data.provider.LocaleProvider
import kotlinx.coroutines.*

class NewsRepositoryImpl(
    val newsDataSource: NewsDataSource,
    val bookmarkedNewsDatabaseDao: BookmarkedNewsDatabaseDao,
    val localeProvider: LocaleProvider
): NewsRepository {


    override suspend fun getNews(country: String?, category: String?, source: String?, q: String?): LiveData<List<ArticleData>> {
        return withContext(Dispatchers.IO) {
            initNewsData(country, category, source, q)
            val newsList = mutableListOf<ArticleData>()
            newsDataSource.downloadedNews.value?.let {
                it.articles.forEach{ article ->
                    var articleData = article.toData()
                    if(bookmarkedNewsDatabaseDao.getArticle(article.url) != null) {articleData.isBookmarked = true}
                    newsList.add(articleData)
                }
            }
            val newsData = MutableLiveData<List<ArticleData>>(newsList)
            return@withContext newsData
        }
    }

    override fun getStatus(): LiveData<Status>{
        return newsDataSource.apiServiceStatus
    }

    private suspend fun initNewsData(country: String?, category: String?, sources: String?, q: String?){
        fetchNews(country, category = category, sources = sources, q = q)
    }

    private suspend fun fetchNews(country: String?, category: String?, sources: String?, q: String?){
        newsDataSource.fetchNews(country, category = category, sources = sources, q = q)
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
}