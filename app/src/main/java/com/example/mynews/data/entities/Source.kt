package com.example.mynews.data.entities

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source (
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
                   val name: String?)