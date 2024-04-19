package com.bluecheese.android.presentation.signin.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bluecheese.android.R
import com.bluecheese.android.presentation.common.BlueCheesePreview
import com.bluecheese.android.presentation.signin.SignInIntent
import com.bluecheese.android.presentation.signin.SignInState
import com.bluecheese.android.ui.components.atoms.Button
import com.bluecheese.android.ui.components.atoms.Spacer16
import com.bluecheese.android.ui.components.molecules.OutlinedPasswordField
import com.bluecheese.android.ui.theme.ButtonSize
import com.bluecheese.android.ui.theme.PreviewScreen
import com.bluecheese.mvi.compose.rememberMviComponent
import com.bluecheese.mvi.foundation.Model


private data class Actions(
    val onChangeEmail: (String) -> Unit,
    val onChangePassword: (String) -> Unit,
    val onChangeConfirmPassword: (String) -> Unit,
    val onSignup: () -> Unit,
    val onBack: () -> Unit,
) {
    companion object {
        val NoActions = Actions({}, {}, {}, {}, {})
    }
}

@Composable
fun SignUpScreen(model: Model<SignInState, SignInIntent>, onBack: () -> Unit) {
    val (state, onIntent) = rememberMviComponent(model = model)

    SignUpScreen(
        state = state,
        actions = Actions(
            onChangeEmail = { SignInIntent.ChangeEmail(it).let(onIntent) },
            onChangePassword = { SignInIntent.ChangePassword(it).let(onIntent) },
            onChangeConfirmPassword = { SignInIntent.ChangeConfirmPassword(it).let(onIntent) },
            onSignup = { SignInIntent.Signup.let(onIntent) },
            onBack = onBack
        )
    )
}

@Composable
private fun SignUpScreen(
    state: SignInState,
    actions: Actions
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 48.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.email,
        isError = state.isLoginFailed,
        label = { Text(text = stringResource(id = R.string.email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onValueChange = actions.onChangeEmail
    )
    Spacer16()
    OutlinedPasswordField(
        modifier = Modifier.fillMaxWidth(),
        value = state.password,
        isError = state.isSignUpFailed,
        label = stringResource(id = R.string.password),
        onValueChange = actions.onChangePassword
    )
    Spacer16()
    OutlinedPasswordField(
        modifier = Modifier.fillMaxWidth(),
        value = state.confirmPassword,
        isError = state.isSignUpFailed,
        supportingText = state.signUpError,
        label = stringResource(id = R.string.confirm_password),
        onValueChange = actions.onChangeConfirmPassword
    )
    Spacer16()
    Button(
        text = stringResource(id = R.string.create_account),
        isLoading = state.isLoginLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonSize.Medium),
        onClick = actions.onSignup
    )
    Spacer16()
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonSize.Medium),
        onClick = actions.onBack
    ) {
        Text(text = stringResource(id = R.string.back))
    }
}

@PreviewScreen
@Composable
private fun SignInScreenPreview() = BlueCheesePreview {
    SignUpScreen(state = SignInState(), actions = Actions.NoActions)
}