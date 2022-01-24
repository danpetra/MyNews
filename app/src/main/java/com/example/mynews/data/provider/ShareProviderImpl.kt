package com.example.mynews.data.provider

import android.content.Intent

class ShareProviderImpl : ShareProvider {
    override fun getShareIntent(message: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, message)
        return shareIntent
    }
}