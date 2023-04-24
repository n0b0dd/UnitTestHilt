package com.kosign.multimodulehilt.data.model

import com.kosign.multimodulehilt.data.model.CurrentCondition
import com.squareup.moshi.Json

data class LocalWeather(
    @field:Json(name = "current") val current: CurrentCondition? = null
)
