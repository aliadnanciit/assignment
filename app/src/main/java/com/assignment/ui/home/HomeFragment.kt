package com.assignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.assignment.R
import com.assignment.databinding.FragmentHomeBinding
import com.assignment.model.UIState
import com.assignment.viewmodel.ShortURLViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: ShortURLViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.buttonShorten.setOnClickListener {

//            findNavController().navigate(R.id.navigation_history)
            if(binding.edittextLink.text.toString().isNotEmpty()) {
                viewModel.createShortUrl(binding.edittextLink.text.toString())
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiStateLiveData.observe(viewLifecycleOwner, Observer {
            processUIState(it)
        })
    }

    private fun processUIState(uiState: UIState) {
        when(uiState) {
            is UIState.Loading -> {

            }
            is UIState.Success -> {

            }
            else -> {

            }
        }
    }
}