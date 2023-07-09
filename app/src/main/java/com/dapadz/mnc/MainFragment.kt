package com.dapadz.mnc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?,
    ) : View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val containerButton = view.findViewById<MaterialCardView>(R.id.button_container)
        val axisXButton = view.findViewById<MaterialCardView>(R.id.button_axis_x)
        val axisYButton = view.findViewById<MaterialCardView>(R.id.button_axis_y)
        val axisZButton = view.findViewById<MaterialCardView>(R.id.button_axis_z)
        val fadeThroughButton = view.findViewById<MaterialCardView>(R.id.button_fade)

        containerButton.setOnClickListener {
            val extras = FragmentNavigatorExtras(it to "shared_element_container")
            findNavController().navigate(R.id.action_mainFragment_to_containerFragment, null, null, extras)
        }
        axisXButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_axisXFragment)
        }
        axisYButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_axisYFragment)
        }
        axisZButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_axisZFragment)
        }
        fadeThroughButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fadeThroughFragment)
        }

        return view
    }

}