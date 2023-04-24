package com.kosign.multimodulehilt.data.api

import okhttp3.Interceptor

class UrlInterceptorHolder(
    val urlInterceptor: Interceptor
)