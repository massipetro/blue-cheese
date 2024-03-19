package com.bluecheese.android.presentation.login

import android.util.Log
import com.bluecheese.android.navigation.NavigationParameter
import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.navigation.navigateTo
import com.bluecheese.android.presentation.common.RoutingViewModel
import com.bluecheese.domain.interactor.LoginUseCase
import com.bluecheese.domain.onEachFail
import com.bluecheese.mvi.extension.concatReducers
import com.bluecheese.mvi.foundation.Model
import com.bluecheese.mvi.foundation.Store
import com.bluecheese.mvi.viewmodel.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    router: RouterImpl,
    private val store: Store<LoginState>,
    private val reducers: LoginReducers,
    private val loginUseCase: LoginUseCase,
) : Model<LoginState, LoginIntent>, RoutingViewModel(router) {
    override fun subscribeTo(intents: Flow<LoginIntent>) = stateMachine(
        store = store,
        intents = intents
    ) {
        on<LoginIntent.ChangeEmail>() updateState reducers::changeEmail
        on<LoginIntent.ChangePassword>() updateState reducers::changePassword

        on<LoginIntent.Login>() sideEffect {
            updateState(reducers.resetLoginError)

            navigateTo(NavigationParameter.Home)

            LoginUseCase.Params(currentState.email, currentState.password)
                .let { loginUseCase.perform(it) }
                .onStart {
                    updateState(reducers.startLoginLoading)
                }
                .onEachFail {
                    Log.w("Login", "SignIn Failure: ${it.message}")
                    concatReducers(
                        reducers.stopLoginLoading,
                        reducers.setLoginFailed
                    ).let(::updateState)
                }
                .onEach {
                    Log.d("Login", "SignIn Successful: ${currentState.email}")
                    concatReducers(
                        reducers.stopLoginLoading,
                        reducers.resetLoginError
                    ).let(::updateState)
                    // TODO create saveCurrentUser UseCase
                    // TODO navigateTo(Home)
                    navigateTo(NavigationParameter.Home)
                }
        }
    }
}