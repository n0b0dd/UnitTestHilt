package com.kosign.multimodulehilt.data.model

import com.squareup.moshi.Json

data class Room(
    @field:Json(name = "id") var id: Int = 0,
    @field:Json(name = "name") var name: String? = null
)
