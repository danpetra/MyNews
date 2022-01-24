package com.example.mynews.data.entities

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article (
    @SerializedName("source")
    val source: Source,
    @SerializedName("author")
                    val author: String?,
    @SerializedName("author")
                    val title: String?,
    @SerializedName("author")
                    val description: String?,
    @SerializedName("author")
                    val url: String,
    @SerializedName("author")
                    val urlToImage: String?,
    @SerializedName("author")
                    val publishedAt: String?,
    @SerializedName("content")
                    val content: String?)