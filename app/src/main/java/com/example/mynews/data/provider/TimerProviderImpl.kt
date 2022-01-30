package com.example.mynews.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import androidx.preference.PreferenceManager


object TimerProviderImpl : TimerProvider {
    private lateinit var appContext: Context
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    private var startTime= 0L
    private var previousTime = 0L

    override fun create(context: Context){
        appContext = context.applicationContext
    }

    override fun start(){
        startTime = SystemClock.elapsedRealtime()
        previousTime = preferences.getLong("LAST_SCREEN_TIME", 0L)
    }

    override fun stop(){
        preferences.edit()
            .putLong("LAST_SCREEN_TIME", SystemClock.elapsedRealtime() - startTime + previousTime)
            .apply()
    }

    override fun getTime():Long{
        return SystemClock.elapsedRealtime() - startTime + previousTime
    }

    fun Long.timeToString(): String{
        val allSecs = this / 1000
        val hours = allSecs / 3600
        val mins = (allSecs % 3600) / 60
        val secs = allSecs % 60
        return "${hours}:${mins}:${secs}"
    }


}