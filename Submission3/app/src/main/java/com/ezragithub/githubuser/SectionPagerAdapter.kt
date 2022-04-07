package com.ezragithub.githubuser

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ezra.githubuser.R

class SectionPagerAdapter(private var context: Context, FragmentManager: FragmentManager, bundle: Bundle) : FragmentPagerAdapter(FragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var dataBundle: Bundle
    init {
        dataBundle = bundle
    }


    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab1, R.string.tab2)
    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getItem(row: Int): Fragment {
        var fragmentPager: Fragment? = null
        when(row) {
            0 -> fragmentPager = FollowersFragment()
            1 -> fragmentPager = FollowingFragment()
        }
        fragmentPager?.arguments = this.dataBundle
        return fragmentPager as Fragment
    }
}