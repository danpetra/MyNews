package com.example.mynews.data.db.sources

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynews.data.entities.SourceData

@Database(entities = [SourceData::class], version = 1, exportSchema = false)
abstract class SourcesDatabase: RoomDatabase() {
    abstract fun sourcesDatabaseDao(): SourcesDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE: SourcesDatabase? = null

        fun getInstance(context: Context): SourcesDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SourcesDatabase::class.java,
                        "sources_database.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                Log.i("db", "Source database is initialized")
                return instance
            }

        }
    }
}
