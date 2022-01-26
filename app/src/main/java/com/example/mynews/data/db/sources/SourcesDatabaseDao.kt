package com.example.mynews.data.db.sources

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mynews.data.entities.ArticleData
import com.example.mynews.data.entities.SourceData

@Dao
interface SourcesDatabaseDao {
    @Insert
    fun insert(article: SourceData)
    @Update
    fun update(article: SourceData)
    @Query("SELECT * FROM sources_table WHERE id = :key")
    fun getSource(key: String): SourceData?
    @Query("DELETE FROM sources_table")
    fun clear()
    @Query("SELECT * FROM sources_table ORDER BY name LIMIT 1")
    fun getLast(): LiveData<SourceData>
    @Query("SELECT * FROM sources_table ORDER BY name")
    fun getAll(): LiveData<List<SourceData>>
    @Query("SELECT * FROM sources_table ORDER BY name")
    fun getAllPlain(): List<SourceData>?
    @Query("SELECT * FROM sources_table WHERE country = :country ORDER BY name")
    fun getAllForCountry(country: String): List<SourceData>?
    @Query("SELECT * FROM sources_table WHERE category = :category ORDER BY name")
    fun getAllForCategory(category: String): List<SourceData>?
    @Query("SELECT * FROM sources_table WHERE category = :category AND country = :country ORDER BY name")
    fun getAllForCountryAndCategory(country: String, category: String): List<SourceData>?
    @Query("DELETE FROM sources_table WHERE id = :key")
    fun deleteSource(key:String)
}