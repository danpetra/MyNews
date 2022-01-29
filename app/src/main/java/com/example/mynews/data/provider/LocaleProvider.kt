package com.example.mynews.data.provider

import java.util.*

interface LocaleProvider {
    fun getLocale(): String
    fun getLang(): String
}