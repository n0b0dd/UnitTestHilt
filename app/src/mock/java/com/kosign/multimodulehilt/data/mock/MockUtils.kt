package com.kosign.multimodulehilt.data.mock

import com.kosign.multimodulehilt.util.ResourceUtils
import okhttp3.mockwebserver.MockResponse

object MockUtils {
    val default = failure(404, "")

    fun success(file: String) =
        MockResponse().setResponseCode(200).setBody(
            ResourceUtils.getJsonString("json/$file")
        )

    fun failure(code: Int, file: String) =
        MockResponse().setResponseCode(code).setBody(
            ResourceUtils.getJsonString("json/$file")
        )
}