package com.dapadz.lib.navigation

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.res.use
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.dapadz.lib.R
import com.dapadz.lib.getEnum
import com.dapadz.lib.transition.MotionTransitionType

@NavDestination.ClassType(Fragment::class)
class MotionDestination(
    navigator: Navigator<out FragmentNavigator.Destination>,
) : FragmentNavigator.Destination(navigator) {

    var animationType = MotionTransitionType.FADE_THROUGH
        private set

    override fun onInflate(context : Context, attrs : AttributeSet) {
        super.onInflate(context, attrs)
        context.resources.obtainAttributes(attrs, R.styleable.MotionNavigator).use { array ->
            animationType = array.getEnum(R.styleable.MotionNavigator_animationType, MotionTransitionType.FADE_THROUGH)
        }
    }
}