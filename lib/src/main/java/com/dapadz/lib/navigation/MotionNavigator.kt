package com.dapadz.lib.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.dapadz.lib.transition.MotionTransitionHelper

@Navigator.Name("motion_fragment")
class MotionNavigator(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val containerId: Int
): FragmentNavigator(context, fragmentManager, containerId) {

    private val savedIds = mutableSetOf<String>()
    private val motionHelper = MotionTransitionHelper()

    override fun createDestination() : MotionDestination {
        return MotionDestination(this)
    }

    override fun popBackStack(popUpTo : NavBackStackEntry, savedState : Boolean) {
        if (fragmentManager.isStateSaved) {
            Log.i(TAG, "Ignoring popBackStack() call: FragmentManager has already saved its state")
            return
        }
        if (savedState) {
            val beforePopList = state.backStack.value
            val initialEntry = beforePopList.first()
            val poppedList = beforePopList.subList(
                beforePopList.indexOf(popUpTo),
                beforePopList.size
            )
            for (entry in poppedList.reversed()) {
                if (entry == initialEntry) {
                    Log.i(TAG, "FragmentManager cannot save the state of the initial destination $entry")
                } else {
                    fragmentManager.saveBackStack(entry.id)
                    savedIds += entry.id
                }
            }
        } else {
            fragmentManager.popBackStack(
                popUpTo.id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        state.pop(popUpTo, savedState)
    }

    override fun navigate(
        entries : List<NavBackStackEntry>,
        navOptions : NavOptions?,
        navigatorExtras : Navigator.Extras?
    ) {
        if (fragmentManager.isStateSaved) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already saved its state")
            return
        }
        for (entry in entries) {
            navigate(entry, navOptions, navigatorExtras)
        }
    }

    private fun navigate(
        entry: NavBackStackEntry,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        val initialNavigation = state.backStack.value.isEmpty()

        val restoreState = (
                navOptions != null &&
                !initialNavigation &&
                navOptions.shouldRestoreState() &&
                savedIds.remove(entry.id)
        )

        if (restoreState) {
            fragmentManager.restoreBackStack(entry.id)
            state.push(entry)
            return
        }

        val ft = createFragmentTransaction(entry)

        if (!initialNavigation) { ft.addToBackStack(entry.id) }

        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key, value)
            }
        }

        ft.commit()
        state.push(entry)
    }

    private fun createFragmentTransaction(
        entry: NavBackStackEntry
    ): FragmentTransaction {
        val destination = entry.destination as Destination
        val args = entry.arguments
        var className = destination.className
        if (className[0] == '.') { className = context.packageName + className }
        val frag = fragmentManager.fragmentFactory.instantiate(context.classLoader, className)
        val previousFragment = fragmentManager.findFragmentById(containerId)
        frag.arguments = args
        if (destination is MotionDestination && frag != previousFragment ) {
            val type = destination.animationType
            motionHelper.setupTransition(
                type = type,
                enterFragment = frag,
                exitFragment = previousFragment
            )
        }
        val ft = fragmentManager.beginTransaction()
        ft.replace(containerId, frag)
        ft.setPrimaryNavigationFragment(frag)
        ft.setReorderingAllowed(true)
        return ft
    }


    override fun onSaveState(): Bundle? {
        if (savedIds.isEmpty()) {
            return null
        }
        return bundleOf(KEY_SAVED_IDS to ArrayList(savedIds))
    }

    override fun onRestoreState(savedState: Bundle) {
        val savedIds = savedState.getStringArrayList(KEY_SAVED_IDS)
        if (savedIds != null) {
            this.savedIds.clear()
            this.savedIds += savedIds
        }
    }

    private companion object {
        private const val TAG = "FragmentNavigator"
        private const val KEY_SAVED_IDS = "androidx-nav-fragment:navigator:savedIds"
    }

}