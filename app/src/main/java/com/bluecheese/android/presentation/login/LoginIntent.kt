package com.bluecheese.android.presentation.login

import com.bluecheese.mvi.foundation.Intent

sealed interface LoginIntent : Intent {
    data class ChangeEmail(val email: String) : LoginIntent
    data class ChangePassword(val password: String) : LoginIntent
    data object GoogleLogin : LoginIntent
    data object Login : LoginIntent
    data object Signup : LoginIntent
}