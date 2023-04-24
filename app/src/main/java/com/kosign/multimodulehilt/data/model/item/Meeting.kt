package com.kosign.multimodulehilt.data.model.item

class Meeting(
    var stickColor: String,
    var room: String?,
    var meetingTitle: String?,
    var nowBarShowed: Boolean?,
    var movePosition: Int,
    var start_time : String? = null,
    var end_time : String? = null
)
