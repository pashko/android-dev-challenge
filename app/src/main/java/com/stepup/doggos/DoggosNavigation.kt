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
package com.stepup.doggos

import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.stepup.doggos.details.view.DoggoDetailsScreen
import com.stepup.doggos.details.view.DoggoDetailsViewModel
import com.stepup.doggos.list.view.DoggosListScreen
import com.stepup.doggos.list.view.DoggosListViewModel
import com.stepup.doggos.model.Doggo

@Composable
fun DoggoNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Nav.list.id) {
        composable(Nav.list.id) { entry ->
            val viewModel = viewModel<DoggosListViewModel>(factory = HiltViewModelFactory(context, entry))
            DoggosListScreen(viewModel.feature) {
                navController.navigate(Nav.details.build(it.id))
            }
        }
        composable(Nav.details.id) { entry ->
            val viewModel = viewModel<DoggoDetailsViewModel>(factory = HiltViewModelFactory(context, entry))
            entry.arguments?.let { Nav.details.idFrom(it) }?.let { id ->
                DoggoDetailsScreen(
                    viewModel.getFeatureFor(Doggo.Id(id)),
                    goBack = navController::popBackStack,
                    openUrl = {
                        context.startActivity(Intent(Intent.ACTION_VIEW, it.url.toUri()))
                    }
                )
            }
        }
    }
}

abstract class Destination(val id: String)

@Suppress("ClassName")
private object Nav {

    private const val cocktailId = "cocktail"
    private const val detailsId = "details"

    object list : Destination("list") {
        fun build() = id
    }

    object details : Destination("$detailsId/{$cocktailId}") {

        fun build(id: String) = "$detailsId/$id"

        fun idFrom(bundle: Bundle) = bundle.getString(cocktailId)
    }
}
