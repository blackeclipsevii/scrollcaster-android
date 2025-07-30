package com.example.scrollcaster

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.net.toUri


class WebViewClientImpl(activity: Activity?) : WebViewClient() {
    private var activity: Activity? = null

    init {
        this.activity = activity
    }

    override fun shouldOverrideUrlLoading(webView: WebView, request: WebResourceRequest): Boolean {
        val url = request.url;
        val host = request.url.host ?: return false;

        if (host.indexOf("scrollcaster") > -1) return false

        val intent = Intent(Intent.ACTION_VIEW, url)
        activity!!.startActivity(intent)
        return true
    }
}