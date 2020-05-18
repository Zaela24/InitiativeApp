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
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.database.CharacterDao
import com.zaelaroseapps.initiativeapp.database.CharacterDatabase
import com.zaelaroseapps.initiativeapp.databinding.HomeFragmentBinding
import kotlinx.coroutines.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    val thisJob = Job()


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
                val database = CharacterDatabase
                    .getInstance(requireNotNull(context))
                    .characterDao
                // TODO( rewrite coroutine to be cleaner; remember to fix autoinc issue )
                CoroutineScope(Dispatchers.Main + thisJob).launch {
                    withContext(Dispatchers.IO) {
                        database.clear()
                        database.addNewCharacter(Character())
                    }
                }
                val navController = binding.root.findNavController()
                navController.navigate(R.id.action_homeFragment_to_newGameFragment)
                viewModel.navigateToNewGame
            }
        })

        return binding.root
    }

    override fun onStop() {
        thisJob.cancel()
        super.onStop()
    }
}