/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stepup.doggos.common

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.request.ImageRequest
import com.stepup.doggos.model.Url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@Composable
fun CoilImage(
    url: Url?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: @Composable () -> Unit = {
        Box(
            Modifier.background(
                if (MaterialTheme.colors.isLight) Color.LightGray else Color.DarkGray
            )
        )
    }
) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current
    LaunchedEffect(url) {
        val request = ImageRequest.Builder(context)
            .data(url?.url)
            .build()
        val drawable = Coil.imageLoader(context).execute(request)
            .drawable as? BitmapDrawable
        image = drawable?.bitmap?.asImageBitmap()
    }
    Box(modifier, contentAlignment = Alignment.Center) {
        image?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                alignment = alignment,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize()
            )
        } ?: placeholder()
    }
}

@Composable
inline fun <T> Flow<T>.Collect(crossinline collector: suspend (T) -> Unit) {
    LaunchedEffect(this) {
        collect(collector)
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = Modifier
            .clickable(onClick = onClick)
            .height(56.dp)
            .width(56.dp)
            .padding(12.dp)
    )
}

@Composable
fun Flow<String>.collectAsSnackbarState(): SnackbarHostState {
    return remember { SnackbarHostState() }.apply {
        Collect {
            showSnackbar(it)
        }
    }
}

@Composable
fun CenterAlignBox(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        content()
    }
}
