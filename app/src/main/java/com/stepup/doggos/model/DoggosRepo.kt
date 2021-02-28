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
package com.stepup.doggos.model

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DoggosRepo @Inject constructor(
    private val dao: DoggosDao
) {

    val list get() = flow {
        emit(dao.list().map { DoggosListItem(it.key, it.value) })
    }

    fun details(id: Doggo.Id) = flow {
        emit(
            DoggoDetailsItem(
                id = id,
                doggo = dao.getShortFor(id) ?: return@flow,
                details = dao.getDetailsFor(id)
            )
        )
    }
}

data class DoggosListItem(
    val id: Doggo.Id,
    val doggo: Doggo.Short
)

data class DoggoDetailsItem(
    val id: Doggo.Id,
    val doggo: Doggo.Short,
    val details: Doggo.Details?
)
