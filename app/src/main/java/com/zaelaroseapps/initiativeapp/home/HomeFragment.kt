// Copyright 2020 Zaela Rose

package com.zaelaroseapps.initiativeapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.zaelaroseapps.initiativeapp.R
import com.zaelaroseapps.initiativeapp.databinding.HomeFragmentBinding
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private val thisJob = Job()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToNewGame.observe(viewLifecycleOwner,
        Observer { shouldNavigate ->
            if(shouldNavigate == true) {
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_homeFragment_to_newGameFragment)
                viewModel.navigateToNewGame
            }
        })

        viewModel.navigateToGame.observe(viewLifecycleOwner,
            Observer { shouldNavigate ->
                if (shouldNavigate == true) {
                    val navController = binding.root.findNavController()
                    navController.navigate(R.id.action_homeFragment_to_gameFragment)
                    viewModel.navigateToGame
                }
            })

        return binding.root
    }

    override fun onStop() {
        thisJob.cancel()
        super.onStop()
    }
}