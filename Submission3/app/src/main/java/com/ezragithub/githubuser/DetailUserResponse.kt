package com.ezragithub.githubuser

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(
    @field:SerializedName("login")
    val login: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("followers")
    val followers: String,

    @field:SerializedName("following")
    val following: String?,

    val name : String?,
    @field:SerializedName("public_repos")
    val repository: Int,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,



):Parcelable
