package com.assignment.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assignment.R
import com.assignment.databinding.FragmentCarousalBinding

class CarousalFragment : Fragment() {
    private lateinit var binding : FragmentCarousalBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentCarousalBinding.inflate(inflater, container, false)
        binding.actionSkip.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
        return binding.root
    }
}