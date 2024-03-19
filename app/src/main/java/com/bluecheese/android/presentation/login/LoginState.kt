package com.bluecheese.android.presentation.login

import com.bluecheese.mvi.foundation.State

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoginFailed: Boolean = false,
    val isLoginLoading: Boolean = false,
) : State