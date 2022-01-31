package com.example.mynews

import com.example.mynews.data.repository.NewsDataSourceImpl
import android.app.Application
//import com.example.mynews.data.db.CurrentNewsDatabase
import com.example.mynews.data.db.bookmarks.BookmarkedNewsDatabase
import com.example.mynews.data.db.sources.SourcesDatabase
import com.example.mynews.data.repository.NewsRepositoryImpl
import com.example.mynews.data.network.NewsApiService
import com.example.mynews.data.provider.LocaleProviderImpl
import com.example.mynews.data.provider.ShareProviderImpl
import com.example.mynews.data.provider.SharedPreferencesProviderImpl
import com.example.mynews.data.provider.TimerProviderImpl
import com.example.mynews.data.repository.BookmarksDataSourceImpl
import com.example.mynews.data.repository.BookmarksRepositoryImpl
import com.example.mynews.data.repository.sources.SourcesDataSourceImpl
import com.example.mynews.data.repository.sources.SourcesRepositoryImpl
import com.example.mynews.ui.article.ArticleViewModelFactory
import com.example.mynews.ui.bookmarks.BookmarksViewModelFactory
import com.example.mynews.ui.everything.EverythingViewModelFactory
import com.example.mynews.ui.top.TopViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class MyApplication() : Application(), DIAware {
    override val di = DI.lazy{
        import(androidXModule(this@MyApplication))
        bindSingleton { BookmarkedNewsDatabase.getInstance(instance())}
        bindSingleton { instance<BookmarkedNewsDatabase>().bookmarkedNewsDatabaseDao() }
        bindSingleton { SourcesDatabase.getInstance(instance())}
        bindSingleton { instance<SourcesDatabase>().sourcesDatabaseDao() }
        bindSingleton { NewsApiService() }

        bindSingleton { NewsDataSourceImpl(instance()) }
        bindSingleton { BookmarksDataSourceImpl(instance()) }
        bindSingleton { SourcesDataSourceImpl(instance()) }

        bindSingleton { NewsRepositoryImpl(instance(), instance(), instance(), instance()) }
        bindSingleton { BookmarksRepositoryImpl(instance())}
        bindSingleton { SourcesRepositoryImpl(instance(), instance()) }

        bindProvider { TopViewModelFactory(instance(), instance(), instance(), instance(), instance()) }
        bindProvider { EverythingViewModelFactory(instance(), instance(), instance(), instance(), instance()) }
        bindProvider { BookmarksViewModelFactory(instance(), instance()) }
        bindProvider { ArticleViewModelFactory(instance(), instance()) }

        bindSingleton { LocaleProviderImpl(instance()) }
        bindSingleton { SharedPreferencesProviderImpl(instance()) }
        bindProvider { ShareProviderImpl() }

    }

}