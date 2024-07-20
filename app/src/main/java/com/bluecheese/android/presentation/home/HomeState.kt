package com.bluecheese.android.presentation.home

import android.net.Uri
import com.bluecheese.android.ui.components.molecules.Carousel
import com.bluecheese.mvi.foundation.State
import java.util.Calendar

data class HomeState(
    val loading: Boolean = false,
    val photos: List<Carousel.Item>? = null,
    val selectedPhoto: Uri? = null,
) : State {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
}
