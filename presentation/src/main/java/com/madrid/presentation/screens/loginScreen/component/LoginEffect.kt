package com.madrid.presentation.screens.loginScreen.component

sealed class LoginEffect {
    data class  ShowToast(val message: String) : LoginEffect()
    object DismissToast : LoginEffect()
    data class  OnLoginSuccess(val message: String) : LoginEffect()
    data class OpenView(val url: String) : LoginEffect()
}