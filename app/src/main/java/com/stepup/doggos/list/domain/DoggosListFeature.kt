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
package com.stepup.doggos.list.domain

import com.stepup.doggos.common.update
import com.stepup.doggos.model.Doggo
import com.stepup.doggos.model.DoggosListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

interface DoggosListFeature {
    val state: StateFlow<DoggosListState>
    val errors: Flow<DoggosListError>
    val navigation: Flow<DoggosListToDetails>
    fun openDetails(id: Doggo.Id)
}

data class DoggosListState(
    val list: List<DoggosListItem>? = null
)

data class DoggosListToDetails(val id: Doggo.Id)

data class DoggosListError(val message: String? = null)

fun DoggosListFeature(
    scope: CoroutineScope,
    list: Flow<List<DoggosListItem>>
): DoggosListFeature = object : DoggosListFeature {

    override val state = MutableStateFlow(DoggosListState())

    private val mutableErrors = Channel<DoggosListError>(Channel.BUFFERED)
    override val errors: Flow<DoggosListError> = mutableErrors.receiveAsFlow()

    private val mutableNavigation = Channel<DoggosListToDetails>(Channel.BUFFERED)
    override val navigation = mutableNavigation.receiveAsFlow()

    init {
        list.onEach {
            state.update { copy(list = it) }
        }.catch {
            mutableErrors.send(DoggosListError())
        }.launchIn(scope)
    }

    override fun openDetails(id: Doggo.Id) {
        mutableNavigation.offer(DoggosListToDetails(id))
    }
}
