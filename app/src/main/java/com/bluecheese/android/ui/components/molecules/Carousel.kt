package com.bluecheese.android.ui.components.molecules

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.bluecheese.android.presentation.common.BlueCheesePreview
import com.bluecheese.android.ui.theme.PreviewScreen

object Carousel {
    data class Item(
        val uri: Uri,
        val bitmap: ImageBitmap,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
    items: List<Carousel.Item>,
    onItemClick: (Carousel.Item) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberCarouselState(
        itemCount = { items.count() },
    )
    HorizontalMultiBrowseCarousel(
        state = state,
        preferredItemWidth = 200.dp,
        itemSpacing = 10.dp,
        modifier = modifier.fillMaxWidth()
    ) { index ->
        val item = items[index]
        Card(modifier = Modifier
            .height(205.dp)
            .clickable { onItemClick(item) }
        ) {
            Image(
                bitmap = item.bitmap,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@PreviewScreen
@Composable
private fun CarouselPreview(
    @PreviewParameter(CarouselItemPreviewProvider::class)
    state: List<Carousel.Item>
) = BlueCheesePreview {
    Column(Modifier.padding(20.dp)) {
        Carousel(items = state, {})
    }
}

class CarouselItemPreviewProvider : PreviewParameterProvider<List<Carousel.Item>> {
    override val values: Sequence<List<Carousel.Item>> = sequenceOf(
        listOf(
            Carousel.Item(
                Uri.EMPTY,
                createBitmap(200, 300).asImageBitmap()
            ),
            Carousel.Item(
                Uri.EMPTY,
                createBitmap(200, 400).asImageBitmap()
            ),
            Carousel.Item(
                Uri.EMPTY,
                createBitmap(500, 400).asImageBitmap()
            ),
            Carousel.Item(
                Uri.EMPTY,
                createBitmap(500, 400).asImageBitmap()
            )
        )
    )
}