package com.example.mynews.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_articles")
data class ArticleData (
    @Embedded(prefix = "source_")
    val source: Source?,
    @ColumnInfo
    val author: String?,
    @ColumnInfo
    val title: String?,
    @ColumnInfo
    val description: String?,
    @PrimaryKey
    val url: String,
    @ColumnInfo
    val urlToImage: String?,
    @ColumnInfo
    val publishedAt: String?,
    @ColumnInfo
    val content: String?,
    @ColumnInfo
    val userId: String,
    @ColumnInfo
    var isBookmarked: Boolean = false
    )