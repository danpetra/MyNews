package com.example.mynews.data.provider

import android.content.Context

interface TimerProvider {
    fun create(context: Context)
    fun start()
    fun stop()
    fun getTime():Long
}