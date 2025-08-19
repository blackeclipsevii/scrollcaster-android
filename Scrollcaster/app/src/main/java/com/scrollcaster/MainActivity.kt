package com.scrollcaster

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : ComponentActivity() {
    private var webView: WebView? = null

    fun load(top: Int, bottom: Int) {
        var url = "https://scrollcaster.app";
        var hasQuery = false
        if (top > 0) {
            hasQuery = true
            url = "${url}?inset-top=${top}"
        }
        if (bottom > 0) {
            var delim = "?"
            if (hasQuery)
                delim = "&"
            url = "${url}${delim}inset-bottom=${bottom}"
        }
        webView!!.loadUrl(url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callback = this.onBackPressedDispatcher.addCallback(this) {
            webView?.evaluateJavascript("globalThis.canGoBack();", {
                result ->
                if (result == "false")
                    finish()
                else
                    webView?.evaluateJavascript("globalThis.goBack();", null);
            });
        }

        this.webView = findViewById<View>(R.id.webview) as WebView
        var launch = true;
        ViewCompat.setOnApplyWindowInsetsListener(webView!!){ view, insets ->
            if (launch) {
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                load(systemBars.top, systemBars.bottom);
                launch = false;
            }
            WindowInsetsCompat.CONSUMED;
        }

        val webSettings = webView!!.settings
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true

        val webViewClient = WebViewClientImpl(this)
        webView!!.webViewClient = webViewClient
        webView!!.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                Log.d("WebView", consoleMessage.message())
                return true
            }
        }
    }
}