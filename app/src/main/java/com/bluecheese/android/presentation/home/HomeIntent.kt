package com.bluecheese.android.presentation.home

import android.net.Uri
import com.bluecheese.mvi.foundation.Intent

sealed interface HomeIntent : Intent {
    data class SelectDate(val date: Long) : HomeIntent
    data class OpenPhoto(val photoUri: Uri) : HomeIntent
    data object HidePhoto : HomeIntent
    data class SharePhoto(val photoUri: Uri) : HomeIntent
}