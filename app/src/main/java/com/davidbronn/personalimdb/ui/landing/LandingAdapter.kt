package com.davidbronn.personalimdb.ui.landing

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.davidbronn.personalimdb.ui.annotations.MovieType
import com.davidbronn.personalimdb.ui.movieslist.MoviesListFragment
import com.davidbronn.personalimdb.utils.misc.AppConstants

/**
 * Created by Jude on 13/January/2020
 */
class LandingAdapter(manager: FragmentManager) :
    FragmentStatePagerAdapter(
        manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesListFragment.newInstance(MovieType.MOVIES_LATEST)
            1 -> MoviesListFragment.newInstance(MovieType.MOVIES_NOW_PLAYING)
            2 -> MoviesListFragment.newInstance(MovieType.MOVIES_POPULAR)
            3 -> MoviesListFragment.newInstance(MovieType.MOVIES_TOP_RATED)
            4 -> MoviesListFragment.newInstance(MovieType.MOVIES_UPCOMING)
            else -> MoviesListFragment.newInstance(MovieType.MOVIES_UPCOMING)
        }
    }

    override fun getCount(): Int {
        return AppConstants.MOVIE_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return AppConstants.MOVIE_TITLES[position]
    }
}