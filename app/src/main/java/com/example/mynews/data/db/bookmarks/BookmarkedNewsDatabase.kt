package com.example.mynews.data.db.bookmarks

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynews.data.entities.ArticleData

@Database(entities = [ArticleData::class], version = 1, exportSchema = false)
abstract class BookmarkedNewsDatabase: RoomDatabase() {
    abstract fun bookmarkedNewsDatabaseDao(): BookmarkedNewsDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE: BookmarkedNewsDatabase? = null

        fun getInstance(context: Context): BookmarkedNewsDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkedNewsDatabase::class.java,
                        "bookmarked_articles_database.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                Log.i("db", "Bookmark database is initialized")
                return instance
            }

        }
    }
}
