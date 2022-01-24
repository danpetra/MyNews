package com.example.mynews

import com.example.mynews.data.repository.NewsDataSourceImpl
import android.app.Application
//import com.example.mynews.data.db.CurrentNewsDatabase
import com.example.mynews.data.db.bookmarks.BookmarkedNewsDatabase
import com.example.mynews.data.repository.NewsRepositoryImpl
import com.example.mynews.data.network.NewsApiService
import com.example.mynews.data.provider.LocaleProviderImpl
import com.example.mynews.data.provider.ShareProviderImpl
import com.example.mynews.data.repository.BookmarksDataSourceImpl
import com.example.mynews.data.repository.BookmarksRepositoryImpl
import com.example.mynews.ui.article.ArticleViewModelFactory
import com.example.mynews.ui.bookmarks.BookmarksViewModelFactory
import com.example.mynews.ui.top.TopViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class MyApplication() : Application(), DIAware {
    override val di = DI.lazy{
        import(androidXModule(this@MyApplication))
        //bindSingleton { CurrentNewsDatabase.getInstance(instance())}
        //bindSingleton { instance<CurrentNewsDatabase>().currentNewsDatabaseDao() }
        bindSingleton { BookmarkedNewsDatabase.getInstance(instance())}
        bindSingleton { instance<BookmarkedNewsDatabase>().bookmarkedNewsDatabaseDao() }
        bindSingleton { NewsApiService() }
        bindSingleton { NewsDataSourceImpl(instance()) }
        bindSingleton { BookmarksDataSourceImpl(instance()) }
        bindSingleton { NewsRepositoryImpl(instance(), instance(), instance()) }
        bindSingleton { BookmarksRepositoryImpl(instance())}
        bindProvider { BookmarksViewModelFactory(instance(), instance()) }
        bindProvider { TopViewModelFactory(instance(), instance(), instance(), instance()) }
        bindProvider { ArticleViewModelFactory(instance(), instance()) }
        bindSingleton { LocaleProviderImpl(instance()) }
        bindProvider { ShareProviderImpl() }
    }

}