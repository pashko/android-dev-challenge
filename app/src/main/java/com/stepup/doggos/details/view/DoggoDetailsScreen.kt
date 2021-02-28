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
package com.stepup.doggos.details.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stepup.doggos.common.BackButton
import com.stepup.doggos.common.CenterAlignBox
import com.stepup.doggos.common.CoilImage
import com.stepup.doggos.common.Collect
import com.stepup.doggos.common.collectAsSnackbarState
import com.stepup.doggos.details.domain.DoggoDetailsFeature
import com.stepup.doggos.details.domain.DoggoDetailsState
import com.stepup.doggos.list.view.toDisplayAge
import com.stepup.doggos.model.Cooper
import com.stepup.doggos.model.Doggo
import com.stepup.doggos.model.Url
import com.stepup.doggos.ui.theme.DoggosTheme
import kotlinx.coroutines.flow.map

@Composable
fun DoggoDetailsScreen(
    feature: DoggoDetailsFeature,
    goBack: () -> Unit,
    openUrl: (Url) -> Unit
) {
    val state = feature.state.collectAsState().value
    val errorMessages = feature.errors.map { it.message ?: "Failed to load" }
    feature.navigation.Collect { openUrl(it.adoptUrl) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.doggo?.name ?: "Loading...") },
                navigationIcon = { BackButton(goBack) }
            )
        },
        floatingActionButton = {
            state.adopt?.let { adopt ->
                ExtendedFloatingActionButton(
                    text = { Text("Adopt", style = MaterialTheme.typography.h6) },
                    onClick = { adopt() },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Adopt") }
                )
            }
        },
        scaffoldState = rememberScaffoldState(snackbarHostState = errorMessages.collectAsSnackbarState())
    ) {
        DoggoDetails(state, PaddingValues(bottom = 56.dp))
    }
}

@Composable
fun DoggoDetails(
    state: DoggoDetailsState,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    CenterAlignBox {
        when {
            state.isLoading && state.doggo == null -> CircularProgressIndicator()
            state.doggo == null -> Text("Doggo not found")
            else -> DoggoDetails(
                state.doggo,
                state.details,
                Modifier
                    .fillMaxSize()
                    .align(Alignment.TopStart),
                contentPadding
            )
        }
    }
}

@Composable
fun DoggoDetails(
    doggo: Doggo.Short,
    details: Doggo.Details?,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
    ) {
        CoilImage(
            url = doggo.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4 / 3f),
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(4.dp))
            DoggoAttribute("Age", doggo.age.toDisplayAge())
            details?.apply {
                DoggoAttribute("Sex", sex.toDisplaySex())
                DoggoAttribute("Breed", breed.name)
                Spacer(Modifier.height(4.dp))
                DoggoDescription("What I like", likes)
                DoggoDescription("Type of home needed", typeOfHome)
                DoggoDescription("More about me", aboutMe)
            }
        }
    }
}

@Composable
private fun DoggoAttribute(name: String, value: String, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            append(AnnotatedString("$name: ", SpanStyle(fontWeight = FontWeight.Bold)))
            append(value)
        },
        modifier = modifier.padding(bottom = 4.dp),
        style = MaterialTheme.typography.subtitle2
    )
}

@Composable fun DoggoDescription(title: String, description: String?, modifier: Modifier = Modifier) {
    description?.let {
        Column(
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(title, style = MaterialTheme.typography.subtitle1)
            Spacer(Modifier.height(8.dp))
            Text(description, style = MaterialTheme.typography.body1)
        }
    }
}

private fun Doggo.Sex.toDisplaySex() = when (this) {
    Doggo.Sex.Male -> "Male"
    Doggo.Sex.Female -> "Female"
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DoggosTheme {
        DoggoDetails(Cooper.short, Cooper.details)
    }
}
