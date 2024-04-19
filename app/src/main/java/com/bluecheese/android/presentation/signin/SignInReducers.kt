package com.bluecheese.android.presentation.signin

import android.content.IntentSender
import com.bluecheese.mvi.foundation.Reducer
import javax.inject.Inject

interface SignInReducers {
    fun changeEmail(i: SignInIntent.ChangeEmail): Reducer<SignInState>
    fun changePassword(i: SignInIntent.ChangePassword): Reducer<SignInState>
    fun changeConfirmPassword(i: SignInIntent.ChangeConfirmPassword): Reducer<SignInState>
    fun updateIntentSender(d: IntentSender): Reducer<SignInState>
    fun updateSignUpError(d: String?): Reducer<SignInState>

    val setLoginFailed: Reducer<SignInState>
    val resetLoginError: Reducer<SignInState>
    val startLoginLoading: Reducer<SignInState>
    val stopLoginLoading: Reducer<SignInState>
}

class SignInReducersImpl @Inject constructor() : SignInReducers {
    override fun changeEmail(i: SignInIntent.ChangeEmail) = Reducer<SignInState> { s ->
        s.copy(email = i.email)
    }

    override fun changePassword(i: SignInIntent.ChangePassword) = Reducer<SignInState> { s ->
        s.copy(password = i.password)
    }

    override fun changeConfirmPassword(i: SignInIntent.ChangeConfirmPassword) =
        Reducer<SignInState> { s ->
            s.copy(confirmPassword = i.password)
        }

    override fun updateIntentSender(d: IntentSender) = Reducer<SignInState> { s ->
        s.copy(intentSender = d)
    }

    override fun updateSignUpError(d: String?) = Reducer<SignInState> { s ->
        s.copy(signUpError = d)
    }

    override val setLoginFailed = Reducer<SignInState> { s ->
        s.copy(isLoginFailed = true)
    }
    override val resetLoginError = Reducer<SignInState> { s ->
        s.copy(isLoginFailed = false)
    }

    override val startLoginLoading = Reducer<SignInState> { s ->
        s.copy(isLoginLoading = true)
    }
    override val stopLoginLoading = Reducer<SignInState> { s ->
        s.copy(isLoginLoading = false)
    }
}