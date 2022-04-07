package com.ezragithub.githubuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ezragithub.data.local.FavUserDAO
import com.ezragithub.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavUserDAO?
    private var userDb: UserDatabase?

    init{
        userDb= UserDatabase.getDatabase(application)
        userDao=userDb?.favoriteUserDAO()
    }

    fun getFavoriteUser():LiveData<List<FavoriteUser>>? {
        return userDao?.getFavUser()
    }

}