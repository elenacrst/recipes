package com.weightwatchers.ww_exercise_01.data.model

import com.squareup.moshi.Json

data class Recipe(
        @field:Json(name = "image") @Json(name = "image")
        var image: String? = null,

        @field:Json(name = "title") @Json(name = "title")
        var title: String? = null,

        @field:Json(name = "filter") @Json(name = "filter")
        var filter: String? = null
)