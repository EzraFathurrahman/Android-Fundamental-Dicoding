package com.ezragithub.githubuser

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezra.githubuser.R
import com.ezra.githubuser.databinding.ActivityFavBinding

class FavActivity : AppCompatActivity () {

    private lateinit var binding: ActivityFavBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= ListUserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val movewithObjectData=Intent(this@FavActivity, DetailUserActivity::class.java)
                movewithObjectData.putExtra(DetailUserActivity.EXTRA_DATA, data.login)
                movewithObjectData.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                movewithObjectData.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatarUrl)
                movewithObjectData.putExtra(DetailUserActivity.EXTRA_HTML, data.htmlUrl)
                startActivity(movewithObjectData)
            }


        })

        binding.apply{
            itemRow.setHasFixedSize(true)
            itemRow.layoutManager = LinearLayoutManager(this@FavActivity)
            itemRow.adapter= adapter
        }
    viewModel.getFavoriteUser()?.observe(this,{
        if(it!=null){
            val list =mapList(it)
            adapter.AddListUser(list)
        }
    })

    }
    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.id,
                user.avatar_url,
                user.login,
                user.htmlUrl
            )
            listUsers.add(userMapped)

        }
        return listUsers
    }
}



