// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zaelaroseapps.initiativeapp.database.CharacterDao

class NewGameViewModelFactory(private val dataSource: CharacterDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewGameViewModel::class.java)) {
                return NewGameViewModel(dataSource) as T
            }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}