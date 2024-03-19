package com.bluecheese.android.presentation.login

import com.bluecheese.mvi.foundation.Reducer
import javax.inject.Inject

interface LoginReducers {
    fun changeEmail(i: LoginIntent.ChangeEmail): Reducer<LoginState>
    fun changePassword(i: LoginIntent.ChangePassword): Reducer<LoginState>
    val setLoginFailed: Reducer<LoginState>
    val resetLoginError: Reducer<LoginState>
    val startLoginLoading: Reducer<LoginState>
    val stopLoginLoading: Reducer<LoginState>
}

class LoginReducersImpl @Inject constructor() : LoginReducers {
    override fun changeEmail(i: LoginIntent.ChangeEmail) = Reducer<LoginState> { s ->
        s.copy(email = i.email)
    }

    override fun changePassword(i: LoginIntent.ChangePassword) = Reducer<LoginState> { s ->
        s.copy(password = i.password)
    }

    override val setLoginFailed = Reducer<LoginState> { s ->
        s.copy(isLoginFailed = true)
    }

    override val resetLoginError = Reducer<LoginState> { s ->
        s.copy(isLoginFailed = false)
    }

    override val startLoginLoading = Reducer<LoginState> { s ->
        s.copy(isLoginLoading = true)
    }
    override val stopLoginLoading = Reducer<LoginState> { s ->
        s.copy(isLoginLoading = false)
    }
}