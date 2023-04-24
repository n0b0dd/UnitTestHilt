package com.kosign.multimodulehilt.data.model.item

data class Item(var itemType: Int, var dateTime: String, val meeting: Meeting? = null)