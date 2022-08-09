package com.juhnny.dailydiscovery

import androidx.annotation.Keep

@Keep
data class User(val memNo:String,
                val memId:String,
                val email:String,
                val nickname:String,
                val profileMsg:String,
                val signUpDatetime:String,
                val lastLoginDatetime:String,
                val authType:String,
                val authId:String,
                val authConnectDatetime:String
                ) {

}