package com.juhnny.dailydiscovery

data class Photo(var no:String,
                 var topic:String,
                 var description:String,
                 val userId:String,
                 var creationDate:String,
                 var updateDate:String,
                 var src:String){
}