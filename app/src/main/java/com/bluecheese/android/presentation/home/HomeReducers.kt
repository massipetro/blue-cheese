package com.bluecheese.android.presentation.home

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.asImageBitmap
import com.bluecheese.android.ui.components.molecules.Carousel
import com.bluecheese.mvi.foundation.Reducer
import javax.inject.Inject

interface HomeReducers {
    fun updatePhotos(b: Map<Uri, Bitmap>?): Reducer<HomeState>
    fun updateSelectedPhoto(u: Uri?): Reducer<HomeState>
}

class HomeReducersImpl @Inject constructor() : HomeReducers {
    override fun updatePhotos(
        b: Map<Uri, Bitmap>?
    ) = Reducer<HomeState> { s ->
        s.copy(
            photos = b?.map { (uri, bitmap) ->
                Carousel.Item(
                    uri = uri,
                    bitmap = bitmap.asImageBitmap()
                )
            }
        )
    }

    override fun updateSelectedPhoto(
        u: Uri?
    ) = Reducer<HomeState> { s ->
        s.copy(selectedPhoto = u)
    }
}