package com.zaelaroseapps.initiativeapp.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.database.CharacterDao

class GameViewModel(val database: CharacterDao) : ViewModel() {

    private var _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

    private var _characterClicked = MutableLiveData<Int>()
    val characterClicked: LiveData<Int>
        get() = _characterClicked

    fun onCharacterClicked(id: Int) {
        _characterClicked.value = id
    }
}