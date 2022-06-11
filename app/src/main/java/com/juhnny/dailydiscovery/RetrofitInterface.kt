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
    @POST("/DailyDiscovery/loadMemberProfile.php")
    fun loadProfile(@Field("email") email: String):Call<Response<User>>

    @FormUrlEncoded
    @POST("/DailyDiscovery/loadMember.php")
    fun loadMemberString(@Field("email") email: String):Call<String>

    //@Query - 서버에서 인식할 식별자(GET 방식으로 날아갈 Key값)
    @GET("/DailyDiscovery/addTopic.php")
    fun addTopic(@Query("topic") newTopic:String):Call<String>

    @GET("/DailyDiscovery/loadTopic.php")
    fun loadTopic(@Query("numOfItems") numOfItems: Int=10):Call<Response<Topic>>

    @GET("/DailyDiscovery/loadTodayTopic.php")
    fun loadTodayTopicString():Call<String>

    @GET("/DailyDiscovery/loadTodayTopic.php")
    fun loadTodayTopic():Call<Response<Topic>>

    @GET("/DailyDiscovery/loadMainPhotos.php")
    fun loadMainPhotosString():Call<String>

    @GET("/DailyDiscovery/loadMainPhotos.php")
    fun loadMainPhotos():Call<Response<Photo>>

    @GET("/DailyDiscovery/savePost.php")
    fun savePost(@Query("topic") topic:String,
                 @Query("message") message:String,
                 @Query("email") email:String,
                 @Query("imgUrl") imgUrl:String): Call<String> //제네릭으로는 파싱해서 변환할 클래스를 써준다. 메소드 이름은 마음대로..

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

    @FormUrlEncoded
    @POST("/DailyDiscovery/isFollowing.php")
    fun isFollowing(@Field("userEmail") userEmail: String,
                    @Field("targetEmail") targetEmail: String):Call<String> //Boolean이 아닌 String으로 받아서 처리. 오류 코드 보기 더 쉬워서

    @FormUrlEncoded
    @POST("/DailyDiscovery/saveFollow.php")
    fun saveFollow(@Field("userEmail") userEmail:String,
                   @Field("targetEmail") targetEmail:String):Call<String>

    @FormUrlEncoded
    @POST("/DailyDiscovery/saveUnfollow.php")
    fun saveUnfollow(@Field("userEmail") userEmail:String,
                     @Field("targetEmail") targetEmail:String):Call<String>

    @FormUrlEncoded
    @POST("/DailyDiscovery/loadFollowing.php")
    fun loadFollowing(@Field("userEmail") userEmail:String):Call<Response<Follow>>

    @FormUrlEncoded
    @POST("/DailyDiscovery/loadFollowing.php")
    fun loadFollowingString(@Field("userEmail") userEmail:String):Call<String>


}