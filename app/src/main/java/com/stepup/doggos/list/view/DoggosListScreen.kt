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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stepup.doggos.common.CenterAlignBox
import com.stepup.doggos.common.Collect
import com.stepup.doggos.common.collectAsSnackbarState
import com.stepup.doggos.list.domain.DoggosListFeature
import com.stepup.doggos.model.Cooper
import com.stepup.doggos.model.Doggo
import com.stepup.doggos.model.DoggosListItem
import com.stepup.doggos.model.Doreen
import com.stepup.doggos.model.Gwen
import com.stepup.doggos.model.Pudding
import com.stepup.doggos.model.Sadie
import kotlinx.coroutines.flow.map

@Composable
fun DoggosListScreen(
    feature: DoggosListFeature,
    toDetails: (Doggo.Id) -> Unit
) {
    val doggos = feature.state.collectAsState().value
    val errorMessages = feature.errors.map { it.message ?: "Failed to load" }
    feature.navigation.Collect {
        toDetails(it.id)
    }
    Scaffold(
        scaffoldState = rememberScaffoldState(
            snackbarHostState = errorMessages.collectAsSnackbarState()
        ),
        topBar = {
            TopAppBar(title = { Text(text = "Doggos") })
        }
    ) {
        DoggoList(doggos.list, feature::openDetails)
    }
}

@Composable
fun DoggoList(
    items: List<DoggosListItem>?,
    onItemClick: (Doggo.Id) -> Unit
) {
    CenterAlignBox {
        when {
            items == null -> CircularProgressIndicator()
            items.isEmpty() -> Text(text = "No doggos found", Modifier.wrapContentSize())
            else -> LazyColumn(
                contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items, key = { it.id.id }) {
                    DoggoListItem(doggo = it.doggo, onClick = { onItemClick(it.id) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DoggoList(
        listOf(
            DoggosListItem(Doggo.Id("1"), Doreen.short),
            DoggosListItem(Doggo.Id("1"), Sadie.short),
            DoggosListItem(Doggo.Id("1"), Cooper.short),
            DoggosListItem(Doggo.Id("1"), Pudding.short),
            DoggosListItem(Doggo.Id("1"), Gwen.short),
        )
    ) {}
}
