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
package com.stepup.doggos.details.domain

import com.stepup.doggos.common.onLoading
import com.stepup.doggos.common.update
import com.stepup.doggos.model.Doggo
import com.stepup.doggos.model.DoggoDetailsItem
import com.stepup.doggos.model.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

interface DoggoDetailsFeature {
    val state: StateFlow<DoggoDetailsState>
    val errors: Flow<DoggoDetailsError>
    val navigation: Flow<DoggoDetailsAdopt>
}

data class DoggoDetailsState(
    val doggo: Doggo.Short? = null,
    val details: Doggo.Details? = null,
    val isLoading: Boolean = true,
    val adopt: (() -> Unit)? = null,
)

data class DoggoDetailsAdopt(val adoptUrl: Url)

data class DoggoDetailsError(val message: String? = null)

fun DoggoDetailsFeature(
    scope: CoroutineScope,
    details: Flow<DoggoDetailsItem?>
): DoggoDetailsFeature = object : DoggoDetailsFeature {

    override val state = MutableStateFlow(DoggoDetailsState())

    private val mutableErrors = Channel<DoggoDetailsError>(Channel.BUFFERED)
    override val errors = mutableErrors.receiveAsFlow()

    private val mutableNavigation = Channel<DoggoDetailsAdopt>(Channel.BUFFERED)
    override val navigation = mutableNavigation.receiveAsFlow()

    init {
        details.onEach {
            state.update {
                copy(
                    doggo = it?.doggo,
                    details = it?.details,
                    adopt = it?.details?.url?.let {
                        { mutableNavigation.offer(DoggoDetailsAdopt(it)) }
                    }
                )
            }
        }.onLoading {
            state.update { copy(isLoading = it) }
        }.catch {
            mutableErrors.send(DoggoDetailsError())
        }.launchIn(scope)
    }
}
