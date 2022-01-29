package com.example.mynews.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import androidx.preference.PreferenceManager

class SharedPreferencesProviderImpl(context: Context) : SharedPreferencesProvider {
    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun isUsingStaticApi(): Boolean {
        return if (ApplicationInfo.FLAG_DEBUGGABLE!=0) preferences.getBoolean(USE_STATIC_API_PREFERENCE_KEY, false)
        else false
    }

    /*  override fun isSendingNotifications(): Boolean {
          return preferences.getBoolean("show_notification", false)
      }*/
}