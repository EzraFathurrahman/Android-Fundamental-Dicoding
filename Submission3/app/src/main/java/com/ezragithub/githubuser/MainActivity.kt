package com.ezragithub.githubuser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezra.githubuser.R
import com.ezra.githubuser.databinding.ActivityMainBinding
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding : ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ListUserAdapter()


        val switchTheme = findViewById<SwitchMaterial>(R.id.switchTheme)

        val pref = SettingsPreferences.getInstance(dataStore)
        val switchViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SwitchViewModel::class.java
        )
        switchViewModel.getThemeSettings().observe(this,
            { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
            })


        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        fun setUserData(setData : ArrayList<User>){
            binding.itemRow.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.itemRow.adapter = adapter

            binding.btnSearch.setOnClickListener {
                searchUser()
            }
            binding.Query.setOnKeyListener { view, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val movewithObjectData=Intent(this@MainActivity, DetailUserActivity::class.java)
                    movewithObjectData.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    movewithObjectData.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatarUrl)
                    movewithObjectData.putExtra(DetailUserActivity.EXTRA_DATA, data.login)
                    movewithObjectData.putExtra(DetailUserActivity.EXTRA_HTML, data.htmlUrl)
                    startActivity(movewithObjectData)
                }


        })
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        mainViewModel.LoadUser()

        mainViewModel.listUsers.observe(this, { listUsers ->
            setUserData(listUsers)
        }
        )
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.AddListUser(it)
                showLoading(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavActivity::class.java).also{
                    startActivity(it)}
            }
            R.id.settings -> {
                val i = Intent(this, SwitchActivity::class.java)
                startActivity(i)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchUser() {
        binding.apply {
            val query = Query.text.toString()
            if (query==null) {
                return
            }
            Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()

            if((viewModel.setSearchUsers(query)!=null)){
            showLoading(true)}else{
                showLoading(false)
            }
        }
    }
    private fun showLoading(set: Boolean) {
        if (set) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}