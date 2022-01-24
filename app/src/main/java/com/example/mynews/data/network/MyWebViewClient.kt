package com.example.mynews.data.network

import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.Toast
import android.graphics.Bitmap


class MyWebViewClient: WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Toast.makeText(
            view?.context,
            "Page is loaded",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Toast.makeText(
            view?.context,
            "Page loading started",
            Toast.LENGTH_SHORT
        )
            .show()
    }
}