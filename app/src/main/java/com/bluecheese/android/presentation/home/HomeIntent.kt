package com.bluecheese.android.presentation.home

import com.bluecheese.mvi.foundation.Intent

sealed interface HomeIntent : Intent {
    data class SelectDate(val date: Long) : HomeIntent
}