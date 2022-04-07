package com.ezragithub.githubuser

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey
    val id: Int,
    val avatar_url:String,
    val login: String,
    val htmlUrl:String
):Serializable
