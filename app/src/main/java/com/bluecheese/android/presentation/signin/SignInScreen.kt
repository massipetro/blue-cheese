package com.bluecheese.android.presentation.signin


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
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
import com.bluecheese.android.ui.components.atoms.Spacer16
import com.bluecheese.android.ui.components.molecules.OutlinedPasswordField
import com.bluecheese.android.ui.theme.ButtonSize
import com.bluecheese.android.ui.theme.PreviewScreen
import com.bluecheese.mvi.compose.rememberMviComponent
import com.bluecheese.mvi.foundation.Model

private data class SignInActions(
    val onChangeEmail: (String) -> Unit,
    val onChangePassword: (String) -> Unit,
    val onLogin: () -> Unit,
    val onGoogleLogin: () -> Unit,
    val onGoogleSignInResult: (ActivityResult) -> Unit,
    val onCreateAccount: () -> Unit,
    val showSnackBar: (String, SnackbarDuration) -> Unit,
) {
    companion object {
        val NoActions = SignInActions(
            {}, {}, {}, {}, {}, {}, { _, _ -> }
        )
    }
}

@Composable
fun SignInScreen(
    model: Model<SignInState, SignInIntent>,
    showSnackBar: (String, SnackbarDuration) -> Unit,
) {
    val (state, onIntent) = rememberMviComponent(model = model)

    SignInScreen(
        state = state,
        actions = SignInActions(
            onChangeEmail = { SignInIntent.ChangeEmail(it).let(onIntent) },
            onChangePassword = { SignInIntent.ChangePassword(it).let(onIntent) },
            onLogin = { SignInIntent.Login.let(onIntent) },
            onGoogleLogin = { SignInIntent.GoogleLogin.let(onIntent) },
            onCreateAccount = { SignInIntent.CreateAccount.let(onIntent) },
            onGoogleSignInResult = { SignInIntent.GoogleSignInResult(it).let(onIntent) },
            showSnackBar = showSnackBar,
        )
    )
}

@Composable
private fun SignInScreen(
    state: SignInState,
    actions: SignInActions,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 48.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    val authFailedString = stringResource(id = R.string.auth_failed)
    LaunchedEffect(state.isLoginFailed) {
        if (state.isLoginFailed) actions.showSnackBar(authFailedString, SnackbarDuration.Short)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = actions.onGoogleSignInResult
    )

    LaunchedEffect(state.intentSender) {
        launcher.launch(
            IntentSenderRequest.Builder(
                intentSender = state.intentSender ?: return@LaunchedEffect
            ).build()
        )
    }

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
        isError = state.isLoginFailed,
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
        if (state.isLoginLoading) CircularProgressIndicator()
        else Text(text = stringResource(id = R.string.login))
    }
    Spacer16()
    Row(verticalAlignment = Alignment.CenterVertically) {
        HalfDivider()
        Spacer16()
        Text(text = stringResource(id = R.string.or), color = MaterialTheme.colorScheme.secondary)
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
        onClick = actions.onCreateAccount
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
private fun SignInScreenPreview() = BlueCheesePreview {
    SignInScreen(state = SignInState(), actions = SignInActions.NoActions)
}