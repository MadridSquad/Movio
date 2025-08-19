package com.madrid.presentation.screens.loginScreen.component

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String) {
    Column(modifier = Modifier.padding(top = 48.dp)) {


        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = {  context ->
                WebView( context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?,
                                                              request: android.webkit.WebResourceRequest?): Boolean {
                            val requestedUrl = request?.url.toString()
                            return if (requestedUrl.startsWith(url)) {

                                false
                            } else {

                                true
                            }
                        }
                    }

                    loadUrl(url)
                }
            }
        )
    }
}