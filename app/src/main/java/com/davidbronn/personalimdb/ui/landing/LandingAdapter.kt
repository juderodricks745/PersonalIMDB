package com.davidbronn.personalimdb.ui.landing

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.davidbronn.personalimdb.ui.movieslist.MoviesListFragment

/**
 * Created by Jude on 13/January/2020
 */
class LandingAdapter(private val titles: List<String>, manager: FragmentManager) :
    FragmentStatePagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            MoviesListFragment.newInstance("P")
        } else  {
            MoviesListFragment.newInstance("T")
        }
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}