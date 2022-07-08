package com.juhnny.dailydiscovery

data class ResponseBody<T> (val itemCount:Int,
                            val items:List<T>) {

}