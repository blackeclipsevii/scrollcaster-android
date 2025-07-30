package com.example.scrollcaster

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.webView = findViewById<View>(R.id.webview) as WebView
        val webSettings = webView!!.settings
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true;

        val webViewClient = WebViewClientImpl(this)
        webView!!.webViewClient = webViewClient
        webView!!.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d("WebView", consoleMessage.message())
                return true
            }
        }
        webView!!.loadUrl("https://scrollcaster.app?platform=android")
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}