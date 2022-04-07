package com.ezragithub.githubuser

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("html_url")
    val htmlUrl: String
)
