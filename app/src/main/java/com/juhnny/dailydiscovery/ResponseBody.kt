package com.juhnny.dailydiscovery

data class ResponseBody<T> (val items:List<T>,
                            val itemCount:Int) {

}