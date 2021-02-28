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

object Doggo {

    inline class Id(val id: String)

    data class Short(
        val name: String,
        val age: IntRange,
        val imageUrl: Url
    )

    data class Details(
        val sex: Sex,
        val breed: Breed,
        val likes: String?,
        val typeOfHome: String?,
        val aboutMe: String?,
        val url: Url
    )

    inline class Breed(
        val name: String
    )

    enum class Sex {
        Male, Female
    }
}

inline class Url(val url: String)
