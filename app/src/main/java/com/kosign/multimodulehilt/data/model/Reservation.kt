package com.kosign.multimodulehilt.data.model

import com.squareup.moshi.Json

data class Reservation (
    @field:Json(name = "id") var id: Int = 0,
    @field:Json(name = "date") var date: String? = null,
    @field:Json(name = "agenda") var agenda: String? = null,
    @field:Json(name = "start_time") var start_time: String? = null,
    @field:Json(name = "end_time") var end_time: String? = null,
    @field:Json(name = "color") var color: String? = null,
    @field:Json(name = "created_datetime") var created_datetime: String? = null,
    @field:Json(name = "updated_datetime") var updated_datetime: String? = null,
    @field:Json(name = "rooms") var room: Room? = null,
    @field:Json(name = "is_recurring") var is_recurring: Boolean = false,
    @field:Json(name = "users") var user: User? = null
)