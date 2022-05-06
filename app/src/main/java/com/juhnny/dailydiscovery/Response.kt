package com.juhnny.dailydiscovery

data class Response<T>(val responseHeader: com.juhnny.dailydiscovery.ResponseHeader,
                       val responseBody: com.juhnny.dailydiscovery.ResponseBody<T>) {



}
