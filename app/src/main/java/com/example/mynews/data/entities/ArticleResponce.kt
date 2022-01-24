package com.example.mynews.data.entities

import com.google.gson.annotations.SerializedName


data class ArticleResponce (
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
                           val totalResults: Int,
    @SerializedName("articles")
                           val articles: List<Article>)