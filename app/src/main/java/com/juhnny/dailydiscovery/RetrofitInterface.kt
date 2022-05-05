package com.juhnny.dailydiscovery

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    //@Query - 서버에서 인식할 식별자(GET 방식으로 날아갈 Key값)
    @GET("/DailyDiscovery/savePost.php")
    fun savePost(@Query("topic") topic:String,
                 @Query("message") message:String,
                 @Query("email") email:String,
                 @Query("imgUrl") imgUrl:String): Call<Photo> //제네릭으로는 파싱해서 변환할 클래스를 써준다. 메소드 이름은 마음대로..

    @GET("/DailyDiscovery/loadPost.php")
    fun loadWriting(@Query("topic") topic:String,
                    @Query("numOfRows") numofRows:Int=10):Call<Response<Photo>> //여기 수정
}