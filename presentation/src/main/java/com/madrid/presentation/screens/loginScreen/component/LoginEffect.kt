package com.madrid.presentation.screens.loginScreen.component

sealed class LoginEffect {
    data class  ShowToast(val message: Int) : LoginEffect()
    object DismissToast : LoginEffect()
    data class  OnLoginSuccess(val message: String) : LoginEffect()
    data class WebView(val url: String) : LoginEffect()
}