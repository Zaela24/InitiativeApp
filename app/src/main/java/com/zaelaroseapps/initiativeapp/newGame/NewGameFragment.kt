// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zaelaroseapps.initiativeapp.database.CharacterDatabase
import com.zaelaroseapps.initiativeapp.databinding.NewGameFragmentBinding

class NewGameFragment : Fragment() {

    //private lateinit var viewModel: NewGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = NewGameFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        //TODO( "Check if requireNotNull here is safe" )
        val dataSource = CharacterDatabase.getInstance(requireNotNull(context)).characterDao

        val viewModelFactory = NewGameViewModelFactory(dataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(NewGameViewModel::class.java)

        binding.viewModel = viewModel

        val manager = GridLayoutManager(activity, 1)
        binding.characterList.layoutManager = manager

        val adapter = NewCharacterListAdapter(NewCharacterClickListener { charID ->
            viewModel.onCharacterClicked(charID)
        })

        binding.characterList.adapter = adapter

        return binding.root
    }
}