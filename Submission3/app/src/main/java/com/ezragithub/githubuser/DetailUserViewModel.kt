package com.ezragithub.githubuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ezragithub.data.local.FavUserDAO
import com.ezragithub.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel (application: Application): AndroidViewModel(application) {
    private val _User = MutableLiveData<DetailUserResponse>()
    val User: MutableLiveData<DetailUserResponse> = _User

    private var userDAO:FavUserDAO?
    private var userDatabase:UserDatabase?

    init{
        userDatabase= UserDatabase.getDatabase(application)
        userDAO=userDatabase?.favoriteUserDAO()
    }

    fun setUserDetail(login: String) {
        ApiConfig.getApiService()
            .getUserDetail(login)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _User.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return User
    }
    fun addFav(id:Int, avatar_url:String,login:String,htmlUrl:String){
        CoroutineScope(Dispatchers.IO).launch{
            var user =FavoriteUser(
                id,avatar_url,login,htmlUrl)
            userDAO?.addFavorite(user)
            }
        }
    fun removeFavorite(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDAO?.removeFavorite(id)
        }
    }
    fun checkUser(id:Int)= userDAO?.checkUser(id)

    }
