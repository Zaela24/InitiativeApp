package com.zaelaroseapps.initiativeapp.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zaelaroseapps.initiativeapp.database.CharacterDatabase
import com.zaelaroseapps.initiativeapp.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GameFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val dataSource = CharacterDatabase.getInstance(requireNotNull(context)).characterDao

        val viewModelFactory = GameViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        binding.viewModel = viewModel

        val manager = GridLayoutManager(activity, 1)
        binding.characterList.layoutManager = manager

        val adapter = CharacterListAdapter(CharacterClickListener { charID ->
            viewModel.onCharacterClicked(charID)
        })

        binding.characterList.adapter = adapter

        return binding.root
    }
}