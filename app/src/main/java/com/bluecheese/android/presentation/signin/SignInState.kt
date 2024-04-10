package com.bluecheese.android.presentation.signin

import android.content.IntentSender
import com.bluecheese.mvi.foundation.State

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoginFailed: Boolean = false,
    val isLoginLoading: Boolean = false,
    val intentSender: IntentSender? = null,
) : State