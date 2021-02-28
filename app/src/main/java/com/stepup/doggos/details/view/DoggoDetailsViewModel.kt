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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stepup.doggos.common.featureFactory
import com.stepup.doggos.details.domain.DoggoDetailsFeature
import com.stepup.doggos.model.Doggo
import com.stepup.doggos.model.DoggosRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DoggoDetailsViewModel @Inject constructor(
    private val repo: DoggosRepo,
) : ViewModel() {

    private val featureFactory = viewModelScope.featureFactory<Doggo.Id, DoggoDetailsFeature> { id ->
        DoggoDetailsFeature(this, repo.details(id))
    }

    fun getFeatureFor(id: Doggo.Id) = featureFactory.getFeatureFor(id)
}
