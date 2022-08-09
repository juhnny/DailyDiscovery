package com.juhnny.dailydiscovery

import androidx.annotation.Keep

@Keep
data class Response<T>(val responseHeader: ResponseHeader,
                       val responseBody: ResponseBody<T>) {}
