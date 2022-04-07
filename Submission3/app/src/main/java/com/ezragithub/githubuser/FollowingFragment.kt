package com.ezragithub.githubuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezra.githubuser.R
import com.ezra.githubuser.databinding.FragmentFollowBinding

class FollowingFragment : Fragment() {
    private lateinit var viewModel: FollowViewModel
    private lateinit var adapterList: ListUserAdapter
    private lateinit var following: String

    private var _FragmentFollowBinding : FragmentFollowBinding? = null
    private val binding get() = _FragmentFollowBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterList = ListUserAdapter()
        following = arguments?.getString(DetailUserActivity.EXTRA_DATA).toString()
        _FragmentFollowBinding = FragmentFollowBinding.bind(view)

        binding.itemRow.layoutManager = LinearLayoutManager(activity)
        binding.itemRow.adapter = adapterList

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)

        viewModel.setListFollowing(following)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapterList.AddListUser(it)
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