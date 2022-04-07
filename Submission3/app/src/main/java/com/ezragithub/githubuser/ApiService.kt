package com.ezragithub.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    @Headers("Authorization: token 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")
    fun getLoadUser(
    ): Call<ArrayList<User>>

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getDetailFollowers(
        @Path("username") followers: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getDetailFollowing(
        @Path("username") following: String
    ): Call<ArrayList<User>>
}