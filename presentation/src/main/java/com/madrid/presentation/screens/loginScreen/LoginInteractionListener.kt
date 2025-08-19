package com.madrid.presentation.screens.loginScreen

interface LoginInteractionListener {
    fun onUsernameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onLoginClicked()
    fun onLoginAsGuestClicked()
    fun onForgotPasswordClicked()
    fun onSignUpClicked()
    fun onShowPasswordToggled()


}