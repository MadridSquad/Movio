package com.madrid.presentation.screens.loginScreen.component

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.madrid.presentation.navigation.LocalNavController


@Composable
fun ForgotPassword(url: String) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(view: WebView, errorCode: Int,
                                                 description: String, failingUrl: String) {

                    }
                }
                loadUrl(url)
            }
        }
    )
}