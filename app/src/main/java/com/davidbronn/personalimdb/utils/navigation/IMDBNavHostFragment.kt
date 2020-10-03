package com.davidbronn.personalimdb.utils.navigation

import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment

/**
 * Created by Jude on 23/August/2020
 */
class IMDBNavHostFragment : NavHostFragment() {

    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination> {
        return IMDBFragmentNavigator(requireContext(), childFragmentManager, id)
    }
}