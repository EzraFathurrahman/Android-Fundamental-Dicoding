package com.ezragithub.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _FollowersList = MutableLiveData<ArrayList<User>>()
    val followersList: LiveData<ArrayList<User>> = _FollowersList
    private val _FollowingList = MutableLiveData<ArrayList<User>>()
    val followingList: LiveData<ArrayList<User>> = _FollowingList

    fun getListFollowers(): LiveData<ArrayList<User>> {
        return followersList
    }
    fun getListFollowing(): LiveData<ArrayList<User>> {
        return followingList
    }

    fun setListFollowers(followers: String) {
        ApiConfig.getApiService()
            .getDetailFollowers(followers)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        _FollowersList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }
    fun setListFollowing(following: String) {
        ApiConfig.getApiService()
            .getDetailFollowing(following)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        _FollowingList.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }

}