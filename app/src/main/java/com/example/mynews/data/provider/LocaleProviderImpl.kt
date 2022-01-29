package com.example.mynews.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.util.*

class LocaleProviderImpl(context: Context) : LocaleProvider {
    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getLocale(): String {
        val useDeviceLocation = preferences.getBoolean(USE_CURRENT_LOCALE_PREFERENCE_KEY, true)
        var selectedLocale = preferences.getString(LOCALE_PREFERENCE_KEY, "us")
        if (useDeviceLocation) selectedLocale = Locale.getDefault().country.lowercase(Locale.getDefault())
        return selectedLocale.toString()
    }

    override fun getLang(): String {
        val useDeviceLocation = preferences.getBoolean(USE_CURRENT_LOCALE_PREFERENCE_KEY, true)
        var selectedLang = preferences.getString(LANGUAGE_PREFERENCE_KEY, "en")
        if (useDeviceLocation) selectedLang = Locale.getDefault().language.lowercase(Locale.getDefault())
        return selectedLang.toString()
    }
}