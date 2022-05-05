package com.juhnny.dailydiscovery

data class ResponseBody<T> (val items:ArrayList<T>,
                            val itemCount:Int) {

}