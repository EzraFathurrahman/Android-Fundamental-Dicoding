package com.ezragithub.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ezra.githubuser.R
import com.ezra.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab1,
            R.string.tab2

        )
        const val EXTRA_ID="extra_id"
        const val EXTRA_AVATAR="extra_avatar"
        const val EXTRA_HTML="extra_html"
    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getData = Bundle()
        val login = intent.getStringExtra(EXTRA_DATA)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatar_url=intent.getStringExtra(EXTRA_AVATAR)
        val htmlUrl=intent.getStringExtra(EXTRA_HTML)
        showLoading(true)
        getData.putString(EXTRA_DATA, login)
        viewModel = ViewModelProvider(this).get(
            DetailUserViewModel::class.java)
        login?.let { viewModel.setUserDetail(it) }


        viewModel.getUserDetail().observe(this@DetailUserActivity) { DetailUserResponse ->
            binding.tvName.text = DetailUserResponse.name
            binding.tvUsername.text = DetailUserResponse.login
            binding.tvCompany.text = "Company: ${DetailUserResponse.company}"
            binding.tvRepository.text = "Repository: ${DetailUserResponse.repository}"
            binding.tvLocation.text = "Location: ${DetailUserResponse.location}"
            binding.tvFollowers.text = "Followers: ${DetailUserResponse.followers}"
            binding.tvFollowing?.text = "Following: ${DetailUserResponse.following}"
            Glide.with(binding.avatarImg.context)
                .load(DetailUserResponse.avatarUrl)
                .circleCrop()
                .into(binding.avatarImg)
            showLoading(false)
        }

        var _isChecked= false

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count!=null){
                    if(count>0){
                        binding.favoriteToggle.isChecked =true
                        _isChecked=true
                    }
                    else{
                        binding.favoriteToggle.isChecked = false
                        _isChecked=false
                    }
                }
            }
        }
        binding.favoriteToggle.setOnClickListener{
           _isChecked= !_isChecked
            if(_isChecked){
                            viewModel.addFav(id,avatar_url!!,login!!,htmlUrl!!)
                Toast.makeText(this, "User Added to Favorite", Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.removeFavorite(id)
                Toast.makeText(this, "User Removed from Favorite", Toast.LENGTH_SHORT).show()
            }
            binding.favoriteToggle.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this@DetailUserActivity, supportFragmentManager, getData)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }
    private fun showLoading(set: Boolean) {
        if (set) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }}
