package com.juhnny.dailydiscovery

data class Response<T>(val responseHeader: Response<T>.ResponseHeader,
                       val responseBody: Response<T>.ResponseBody<T>) {

    inner class ResponseHeader(val resultMsg:String) {
    }

    inner class ResponseBody<T> (val items:List<T>,
                           val itemCount:Int) {

    }

}