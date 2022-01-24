package com.example.mynews.data.provider

import android.content.Intent

interface ShareProvider {
    fun getShareIntent (message: String): Intent
}