package com.juhnny.dailydiscovery

data class Response<T>(val responseHeader: ResponseHeader,
                       val responseBody: ResponseBody<T>) {}
