package com.bluecheese.android.presentation.signin

import android.app.Activity
import android.util.Log
import com.bluecheese.android.navigation.NavigationParameter
import com.bluecheese.android.navigation.RouterImpl
import com.bluecheese.android.navigation.navigateTo
import com.bluecheese.android.presentation.common.RoutingViewModel
import com.bluecheese.domain.integrations.GoogleAuthClient
import com.bluecheese.domain.interactor.LoginUseCase
import com.bluecheese.domain.interactor.SignUpUseCase
import com.bluecheese.mvi.extension.concatReducers
import com.bluecheese.mvi.foundation.Model
import com.bluecheese.mvi.foundation.Store
import com.bluecheese.mvi.viewmodel.stateMachine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    router: RouterImpl,
    private val store: Store<SignInState>,
    private val reducers: SignInReducers,
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val googleAuthClient: GoogleAuthClient,
) : Model<SignInState, SignInIntent>, RoutingViewModel(router) {

    override fun subscribeTo(intents: Flow<SignInIntent>) = stateMachine(
        store = store,
        intents = intents
    ) {
        on<SignInIntent.ChangeEmail>() updateState reducers::changeEmail
        on<SignInIntent.ChangePassword>() updateState reducers::changePassword

        on<SignInIntent.Login>() sideEffect {
            LoginUseCase.Params(currentState.email, currentState.password)
                .let { loginUseCase.perform(it) }
                .onStart {
                    concatReducers(
                        reducers.resetLoginError,
                        reducers.startLoginLoading
                    ).let(::updateState)
                }
                .onEach { result ->
                    result
                        .mapLeft {
                            Log.w("Login", "SignIn Failure: ${it.message}")
                            concatReducers(
                                reducers.setLoginFailed,
                                reducers.stopLoginLoading
                            ).let(::updateState)
                        }
                        .map {
                            Log.d("Login", "SignIn Successful: ${currentState.email}")
                            concatReducers(
                                reducers.stopLoginLoading,
                                reducers.resetLoginError
                            ).let(::updateState)
                            // TODO create saveCurrentUser UseCase
                            navigateTo(NavigationParameter.Home)
                        }
                }
                .collect()
        }

        on<SignInIntent.GoogleSignInResult>() sideEffect { i ->
            if (i.result.resultCode == Activity.RESULT_OK) {
                googleAuthClient
                    .signInWithIntent(intent = i.result.data ?: return@sideEffect)
                    .onEach { if (it.isRight()) navigateTo(NavigationParameter.Home) }
                    .collect()
                // TODO create saveCurrentUser UseCase
            }
        }

        on<SignInIntent.GoogleLogin>() sideEffect {
            googleAuthClient.signIn()
                ?.let { updateState(reducers.updateIntentSender(it)) }
        }

        on<SignInIntent.CreateAccount>() sideEffect {
            concatReducers(
                reducers.changeEmail(SignInIntent.ChangeEmail("")),
                reducers.changePassword(SignInIntent.ChangePassword("")),
            ).let(::updateState)
            navigateTo(NavigationParameter.SignUp)
        }

        on<SignInIntent.Signup>() sideEffect {

        }
    }
}