package com.juhnny.dailydiscovery

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {
    companion object{
        fun getRetrofitInterface():RetrofitInterface {
            val retrofitInterface = Retrofit.Builder().baseUrl("http://iwibest.dothome.co.kr")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitInterface::class.java)
            return retrofitInterface
        }
    }
}