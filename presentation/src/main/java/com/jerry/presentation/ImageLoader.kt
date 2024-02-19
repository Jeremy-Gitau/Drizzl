package com.jerry.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun imageLoader(imageUrl: String): Painter {
    val imagePainter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(false)
                    placeholder(R.drawable._16) // Placeholder drawable while loading
                }).build()
        )

   return imagePainter
}