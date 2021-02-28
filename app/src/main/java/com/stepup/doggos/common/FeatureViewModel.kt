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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

interface FeatureFactory<Seed, Feature> {
    fun getFeatureFor(seed: Seed): Feature
}

fun <Seed, Feature> CoroutineScope.featureFactory(
    makeNewFeature: CoroutineScope.(Seed) -> Feature
) = object : FeatureFactory<Seed, Feature> {

    private fun makeNewScope() = CoroutineScope(coroutineContext.withChildJob())
    private var coroutineScope: CoroutineScope? = null

    private var seed: Seed? = null
    private var feature: Feature? = null

    override fun getFeatureFor(seed: Seed): Feature {
        return feature?.takeIf { this.seed == seed } ?: run {
            this.seed = seed
            coroutineScope?.cancel()
            val scope = makeNewScope().also { coroutineScope = it }
            scope.makeNewFeature(seed).also {
                this.feature = it
            }
        }
    }
}

fun <Feature> CoroutineScope.featureFactory(
    makeNewFeature: CoroutineScope.() -> Feature
) = featureFactory<Unit, Feature> { makeNewFeature() }

val <Feature> FeatureFactory<Unit, Feature>.feature get() = getFeatureFor(Unit)
