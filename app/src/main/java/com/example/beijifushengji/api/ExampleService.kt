package com.example.beijifushengji.api

import retrofit2.http.*

interface ExampleService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }



    @GET("/banner/json")
    suspend fun getBanner(): WanResponse<List<User>>

    @FormUrlEncoded
    @POST("/lg/user_article/add/json")
    suspend fun shareArticle(@Field("title") title: String, @Field("link") url: String): WanResponse<String>

}