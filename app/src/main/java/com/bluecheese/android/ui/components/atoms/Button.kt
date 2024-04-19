package com.bluecheese.android.ui.components.atoms

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluecheese.android.ui.theme.ButtonSize

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) = Button(
    modifier = modifier
        .fillMaxWidth()
        .height(ButtonSize.Medium),
    onClick = onClick
) {
    if (isLoading) CircularProgressIndicator(
        modifier = Modifier
            .fillMaxHeight()
            .aspectRatio(1f),
        color = MaterialTheme.colorScheme.background
    )
    else Text(text = text)
}