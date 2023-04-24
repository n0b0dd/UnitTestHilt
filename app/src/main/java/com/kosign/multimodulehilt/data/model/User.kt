package com.kosign.multimodulehilt.data.model

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "id") var id: String? = null,
    @field:Json(name = "name") var name: String? = null,
    @field:Json(name = "profile") var profile: String? = null,
    @field:Json(name = "username") var username: String? = null
)