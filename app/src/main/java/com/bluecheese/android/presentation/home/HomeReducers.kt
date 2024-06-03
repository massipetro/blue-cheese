package com.bluecheese.android.presentation.home

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.bluecheese.android.ui.components.molecules.Carousel
import com.bluecheese.mvi.foundation.Reducer
import javax.inject.Inject

interface HomeReducers {
    fun updatePhotos(b: List<Bitmap>): Reducer<HomeState>
}

class HomeReducersImpl @Inject constructor() : HomeReducers {
    override fun updatePhotos(
        b: List<Bitmap>
    ) = Reducer<HomeState> { s ->
        s.copy(
            photos = b.map {
                Carousel.Item(
                    id = it.hashCode().toString(),
                    bitmap = it.asImageBitmap()
                )
            }
        )
    }
}