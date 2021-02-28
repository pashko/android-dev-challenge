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
package com.stepup.doggos.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stepup.doggos.common.CoilImage
import com.stepup.doggos.model.Cooper
import com.stepup.doggos.model.Doggo

@Composable
fun DoggoListItem(doggo: Doggo.Short, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = 8.dp
    ) {
        Row(Modifier.fillMaxWidth()) {
            CoilImage(
                url = doggo.imageUrl,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp)
            ) {
                Text(
                    text = doggo.name,
                    style = MaterialTheme.typography.h5,
                )
                Text(text = doggo.age.toDisplayAge())
            }
        }
    }
}

fun IntRange.toDisplayAge(): String {
    return "$start${endInclusive.takeIf { it < Int.MAX_VALUE }?.let { " to $it" } ?: "+"} years"
}

@Preview
@Composable
fun CocktailListItemPreview() {
    DoggoListItem(Cooper.short) {}
}
