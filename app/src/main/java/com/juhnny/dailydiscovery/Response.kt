package com.juhnny.dailydiscovery

data class Response<T>(val responseHeader: com.juhnny.dailydiscovery.ResponseHeader,
                       val responseBody: com.juhnny.dailydiscovery.ResponseBody<T>) {

    public inner class ResponseHeader(val resultMsg:String) {
    }

    inner class ResponseBody<T> (val items:List<T>,
                           val itemCount:Int) {

    }

}