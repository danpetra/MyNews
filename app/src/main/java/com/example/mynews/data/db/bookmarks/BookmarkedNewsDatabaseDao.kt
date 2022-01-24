package com.example.mynews.data.db.bookmarks

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mynews.data.entities.ArticleData

@Dao
interface BookmarkedNewsDatabaseDao {
    @Insert
    fun insert(article: ArticleData)
    @Update
    fun update(article: ArticleData)
    @Query("SELECT * FROM bookmarked_articles WHERE url = :key")
    fun getArticle(key: String): ArticleData?
    @Query("DELETE FROM bookmarked_articles")
    fun clear()
    @Query("SELECT * FROM bookmarked_articles ORDER BY publishedAt DESC LIMIT 1")
    fun getLast(): LiveData<ArticleData>
    @Query("SELECT * FROM bookmarked_articles ORDER BY publishedAt DESC")
    fun getAll(): LiveData<List<ArticleData>>
    @Query("SELECT * FROM bookmarked_articles ORDER BY publishedAt DESC")
    fun getAllPlain(): List<ArticleData>
    @Query("SELECT * FROM bookmarked_articles WHERE :userId ORDER BY publishedAt DESC ")
    fun getAllForUser(userId: String): LiveData<List<ArticleData>>
    @Query("DELETE FROM bookmarked_articles WHERE url = :key")
    fun deleteArticle(key:String)
}