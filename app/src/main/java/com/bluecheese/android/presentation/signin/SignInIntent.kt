package com.bluecheese.android.presentation.signin

import androidx.activity.result.ActivityResult
import com.bluecheese.mvi.foundation.Intent

sealed interface SignInIntent : Intent {
    data class ChangeEmail(val email: String) : SignInIntent
    data class ChangePassword(val password: String) : SignInIntent
    data class GoogleSignInResult(val result: ActivityResult) : SignInIntent
    data object GoogleLogin : SignInIntent
    data object Login : SignInIntent
    data object CreateAccount : SignInIntent
    data object Signup : SignInIntent
}