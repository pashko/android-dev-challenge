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

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

inline fun <T> MutableStateFlow<T>.update(mutation: T.() -> T) {
    value = value.let(mutation)
}

inline fun <T> Flow<T>.onLoading(crossinline block: FlowCollector<T>.(isLoading: Boolean) -> Unit): Flow<T> {
    return onStart { block(true) }.onCompletion { block(false) }
}
