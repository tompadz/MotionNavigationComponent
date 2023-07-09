package com.dapadz.lib.transition

import androidx.fragment.app.Fragment
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis

internal class MotionTransitionHelper {

    fun setupTransition(
        type : MotionTransitionType,
        enterFragment: Fragment?,
        exitFragment: Fragment?
    ) {
        when (type) {
            MotionTransitionType.AXIS_X -> setAxisXTransition(enterFragment, exitFragment)
            MotionTransitionType.AXIS_Y -> setAxisYTransition(enterFragment, exitFragment)
            MotionTransitionType.AXIS_Z -> setAxisZTransition(enterFragment, exitFragment)
            MotionTransitionType.FADE_THROUGH -> setFadeThroughTransition(enterFragment, exitFragment)
            MotionTransitionType.CONTAINER -> setContainerTransformTransition(enterFragment, exitFragment)
        }
    }

    private fun setContainerTransformTransition(
        enterFragment: Fragment?,
        exitFragment: Fragment?
    ) {
        exitFragment?.exitTransition = MaterialElevationScale(false)
        exitFragment?.reenterTransition = MaterialElevationScale(true)
        enterFragment?.enterTransition = null
        enterFragment?.returnTransition = null
        enterFragment?.sharedElementEnterTransition = MaterialContainerTransform()
    }

    private fun setFadeThroughTransition(
        enterFragment: Fragment?,
        exitFragment: Fragment?
    ) {
        exitFragment?.exitTransition = MaterialFadeThrough()
        exitFragment?.reenterTransition = null
        enterFragment?.enterTransition = MaterialFadeThrough()
        enterFragment?.returnTransition = null
    }

    private fun setAxisXTransition(
        enterFragment: Fragment?,
        exitFragment: Fragment?
    ) {
        exitFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X,  true)
        exitFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        enterFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        enterFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    private fun setAxisYTransition(
        enterFragment: Fragment?,
        exitFragment: Fragment?
    ) {
        exitFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y,  true)
        exitFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
        enterFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
        enterFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
    }

    private fun setAxisZTransition(
        enterFragment: Fragment?,
        exitFragment: Fragment?
    ) {
        exitFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z,  true)
        exitFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        enterFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        enterFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

}