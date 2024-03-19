package com.bluecheese.android.presentation.login


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bluecheese.android.R
import com.bluecheese.android.presentation.common.BlueCheesePreview
import com.bluecheese.android.presentation.common.LocalSnackbarHostState
import com.bluecheese.android.ui.components.atoms.Spacer16
import com.bluecheese.android.ui.components.molecules.OutlinedPasswordField
import com.bluecheese.android.ui.theme.ButtonSize
import com.bluecheese.android.ui.theme.PreviewScreen
import com.bluecheese.mvi.compose.rememberMviComponent
import com.bluecheese.mvi.foundation.Model

private data class LoginActions(
    val onChangeEmail: (String) -> Unit,
    val onChangePassword: (String) -> Unit,
    val onLogin: () -> Unit,
    val onGoogleLogin: () -> Unit,
    val onSignup: () -> Unit,
) {
    companion object {
        val NoActions = LoginActions(
            {}, {}, {}, {}, {},
        )
    }
}

@Composable
fun LoginScreen(
    model: Model<LoginState, LoginIntent>
) {
    val (state, onIntent) = rememberMviComponent(model = model)

    LoginScreen(
        state = state,
        actions = LoginActions(
            onChangeEmail = { LoginIntent.ChangeEmail(it).let(onIntent) },
            onChangePassword = { LoginIntent.ChangePassword(it).let(onIntent) },
            onLogin = { LoginIntent.Login.let(onIntent) },
            onGoogleLogin = { LoginIntent.GoogleLogin.let(onIntent) },
            onSignup = { LoginIntent.Signup.let(onIntent) },
        )
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    actions: LoginActions,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 48.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val authFailedString = stringResource(id = R.string.auth_failed)
    LaunchedEffect(state.isLoginFailed) {
        if (state.isLoginFailed) snackbarHostState.showSnackbar(
            message = authFailedString,
        )
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.email,
        label = { Text(text = stringResource(id = R.string.email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onValueChange = actions.onChangeEmail
    )
    Spacer16()
    OutlinedPasswordField(
        modifier = Modifier.fillMaxWidth(),
        value = state.password,
        label = stringResource(id = R.string.password),
        onValueChange = actions.onChangePassword
    )
    Spacer16()
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonSize.Medium),
        onClick = actions.onLogin
    ) {
        Text(text = stringResource(id = R.string.login))
    }
    Spacer16()
    Row(verticalAlignment = Alignment.CenterVertically) {
        HalfDivider()
        Spacer16()
        Text(text = "or", color = MaterialTheme.colorScheme.secondary)
        Spacer16()
        HalfDivider()
    }
    Spacer16()
    FilledTonalButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonSize.Medium),
        onClick = actions.onGoogleLogin
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google_logo),
            tint = Color.Unspecified,
            contentDescription = "google logo"
        )
        Text(text = stringResource(id = R.string.login_google))
    }
    Spacer16()
    FilledTonalButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonSize.Medium),
        onClick = actions.onSignup
    ) {
        Text(text = stringResource(id = R.string.create_account))
    }
}

@Composable
private fun RowScope.HalfDivider() = Box(
    Modifier
        .weight(0.5f)
        .height(1.dp)
        .background(color = DividerDefaults.color)
)

@PreviewScreen
@Composable
private fun LoginScreenPreview() = BlueCheesePreview {
    LoginScreen(state = LoginState(), actions = LoginActions.NoActions)
}