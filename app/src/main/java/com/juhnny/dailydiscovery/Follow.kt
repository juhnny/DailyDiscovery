package com.juhnny.dailydiscovery

import androidx.annotation.Keep

@Keep
data class Follow constructor(var email:String,
                              var nickname:String,
                              var profileMsg:String,
                              var imgUrl1:String,
                              var imgUrl2:String,
                              var imgUrl3:String) {
}