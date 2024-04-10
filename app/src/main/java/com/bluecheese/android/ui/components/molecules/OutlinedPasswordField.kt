package com.bluecheese.android.ui.components.molecules

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.bluecheese.android.R

@Composable
fun OutlinedPasswordField(
    value: String,
    label: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val (visualTransformation, trailingIcon, description) =
        if (passwordVisible) Triple(
            VisualTransformation.None,
            R.drawable.ic_visibility,
            R.string.show_password
        )
        else Triple(
            PasswordVisualTransformation(),
            R.drawable.ic_visibility_off,
            R.string.hide_password
        )

    OutlinedTextField(
        modifier = modifier,
        value = value,
        isError = isError,
        label = { Text(text = label) },
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(id = trailingIcon), stringResource(description))
            }
        }
    )
}