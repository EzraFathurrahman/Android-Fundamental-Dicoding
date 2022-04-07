package com.ezragithub.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ezragithub.githubuser.FavoriteUser
import retrofit2.http.DELETE

@Dao
interface FavUserDAO {
    @Insert
    fun addFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavUser(): LiveData<List<FavoriteUser>>

    @Query("DELETE FROM favorite_user WHERE favorite_user.id=:id")
    fun removeFavorite(id:Int):Int

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id=:id")
    fun checkUser(id:Int):Int

}