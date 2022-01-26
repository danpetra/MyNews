package com.example.mynews.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sources_table")
data class SourceData(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?,
)
{
    override fun toString(): String {
        return this.name ?: ""
    }
}