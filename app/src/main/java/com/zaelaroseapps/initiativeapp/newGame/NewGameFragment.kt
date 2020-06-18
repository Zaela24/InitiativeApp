// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.zaelaroseapps.initiativeapp.R
import com.zaelaroseapps.initiativeapp.database.CharacterDatabase
import com.zaelaroseapps.initiativeapp.databinding.NewGameFragmentBinding


class NewGameFragment : Fragment() {

    private lateinit var viewModel: NewGameViewModel
    lateinit var menuItem: MenuItem

    /**
     * Declare that there will be a menu item
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Setup data binding
     */
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

        viewModel = ViewModelProvider(this, viewModelFactory).get(NewGameViewModel::class.java)

        binding.viewModel = viewModel

        val manager = GridLayoutManager(activity, 1)
        binding.newCharacterList.layoutManager = manager

        val adapter = NewCharacterListAdapter(NewCharacterClickListener { charID ->
            viewModel.onCharacterClicked(charID)
        })

        binding.newCharacterList.adapter = adapter

        // Using viewLifeCycleOwner makes the observer only work while this fragment is on screen
        viewModel.characterList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addFooterAndSubmitList(it)
            }
        })

        return binding.root
    }

    /**
     * Create and inflate menu items
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuItem = menu.add("add")
//        Log.i("NG", menuItem.itemId.toString())  // Used to find ID for onOptionsSelected
        menuItem.setIcon(R.drawable.ic_add_24px)
        menuItem.setShowAsAction(R.menu.new_game_menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Click handling of + button
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> {
                viewModel.onAdd()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}