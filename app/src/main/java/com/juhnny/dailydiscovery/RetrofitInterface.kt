package com.juhnny.dailydiscovery

import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @FormUrlEncoded
    @POST("/DailyDiscovery/saveMember.php")
    fun saveMember(@Field("userId") userId:String,
                   @Field("email") email: String,
                   @Field("nickname") nickname:String):Call<String>

    @FormUrlEncoded
    @POST("/DailyDiscovery/loadMember.php")
    fun loadMember(@Field("email") email: String):Call<Response<User>>

    @FormUrlEncoded
    @POST("/DailyDiscovery/loadMember.php")
    fun loadMemberString(@Field("email") email: String):Call<String>

    //@Query - 서버에서 인식할 식별자(GET 방식으로 날아갈 Key값)
    @GET("/DailyDiscovery/savePost.php")
    fun savePost(@Query("topic") topic:String,
                 @Query("message") message:String,
                 @Query("email") email:String,
                 @Query("imgUrl") imgUrl:String): Call<Photo> //제네릭으로는 파싱해서 변환할 클래스를 써준다. 메소드 이름은 마음대로..

    @GET("/DailyDiscovery/loadPost.php")
    fun loadPost(@Query("topic") topic:String,
                    @Query("numOfItems") numOfItems:Int=10):Call<Response<Photo>>

    @GET("/DailyDiscovery/loadPost.php")
    fun loadPostString(@Query("topic") topic:String,
                 @Query("numOfItems") numOfItems:Int=10):Call<String>

    /********** Album **********/
    @GET("/DailyDiscovery/loadPostToAlbum.php")
    fun loadPostToAlbum(@Query("userEmail") userEmail:String,
                    @Query("numOfItems") numOfItems:Int=10):Call<Response<Photo>>

    @GET("/DailyDiscovery/loadPostToAlbum.php")
    fun loadPostToAlbumString(@Query("userEmail") userEmail:String,
                 @Query("numOfItems") numOfItems:Int=10):Call<String>


}