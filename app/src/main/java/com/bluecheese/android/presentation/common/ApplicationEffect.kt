package com.bluecheese.android.presentation.common

import android.content.Intent
import com.bluecheese.mvi.foundation.Event

sealed interface ApplicationEffect : Event {
    data class LaunchIntent(
        val intent: Intent
    ) : ApplicationEffect
}