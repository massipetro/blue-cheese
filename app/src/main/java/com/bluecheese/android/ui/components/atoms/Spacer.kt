package com.bluecheese.android.ui.components.atoms

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bluecheese.android.ui.theme.Dimen

@Composable
fun Spacer8() = Spacer(modifier = Modifier.size(Dimen.Margin16))

@Composable
fun Spacer16() = Spacer(modifier = Modifier.size(Dimen.Margin16))

@Composable
fun RowScope.SpacerFull() = Spacer(modifier = Modifier.weight(1f))

@Composable
fun ColumnScope.SpacerFull() = Spacer(modifier = Modifier.weight(1f))