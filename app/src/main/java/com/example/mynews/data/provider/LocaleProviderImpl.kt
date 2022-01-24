package com.example.mynews.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class LocaleProviderImpl(context: Context) : LocaleProvider {
    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getLocale(): String {
        val selectedLocale = preferences.getString("locale", "us")
        return selectedLocale.toString()
    }
}