package com.dapadz.lib.navigation

import android.view.View
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.R
import androidx.navigation.plusAssign

class MotionNavHostFragment: NavHostFragment() {

    private val containerId: Int get() = if (id != 0 && id != View.NO_ID) id else R.id.nav_host_fragment_container

    override fun onCreateNavHostController(navHostController : NavHostController) {
        super.onCreateNavHostController(navHostController)
        val navigator = MotionNavigator(
            context = requireContext(),
            fragmentManager = childFragmentManager,
            containerId = containerId,
        )
        navHostController.navigatorProvider += navigator
    }
}