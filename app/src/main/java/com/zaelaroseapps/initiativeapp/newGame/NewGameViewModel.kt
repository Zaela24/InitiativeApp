// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputEditText
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.database.CharacterDao
import kotlinx.coroutines.*

class NewGameViewModel(val database: CharacterDao) : ViewModel() {

//    private var _gameTitle = MutableLiveData<String>()
//    val gameTitle: LiveData<String>
//        get() = _gameTitle

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //TODO( Add doc comments )

    private var _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

//    private var _team = MutableLiveData<String>()
//    val team: LiveData<String>
//        get() = _team
//
//    fun onTeamSelected(teamStr: String) {
//        _team.value = teamStr
//    }
    private var _characterClicked = MutableLiveData<Int>()
    val characterClicked: LiveData<Int>
        get() = _characterClicked

    fun onCharacterClicked(id: Int) {
        _characterClicked.value = id
    }

    private var _surpriseRound = MutableLiveData<Boolean>()
    val surpriseRound: LiveData<Boolean>
        get() = _surpriseRound

    private var _navigateToGame = MutableLiveData<Boolean>()
    val navigateToGame: LiveData<Boolean>
        get() = _navigateToGame

    fun onSurpriseRoundChecked() {
        _surpriseRound.value = true
    }

    fun onSurpriseRoundUnchecked() {
        _surpriseRound.value = false
    }

    fun onNavigateToGame() {
        _navigateToGame.value = true
    }

    fun onNavigatedToGame() {
        _navigateToGame.value = false
    }



    private suspend fun editCharacter(character: Character) {
        withContext(Dispatchers.IO) {
            database.update(character)
        }
    }

    private suspend fun deleteCharacter(charID: Int) {
        withContext(Dispatchers.IO) {
            database.delete(charID)
        }
    }

    private suspend fun fetchCharacters() : List<Character>? {
        var items: List<Character>? = null
        withContext(Dispatchers.IO) {
            items = database.reorder()
        }
        return items
    }

    fun onAdd() {
        uiScope.launch {

            val newCharacter = Character()

            addCharacter(newCharacter)
        }
    }
    private suspend fun addCharacter(character: Character) {
        withContext(Dispatchers.IO) {
            database.addNewCharacter(character)
            //TODO( find another way to update when adding a new character )
//           _characterList.value = database.getAll()
        }
    }

    fun onEdit() {
        uiScope.launch {
            // figure out how to listen to edits for each character
        }
    }

    fun onDelete() {
        uiScope.launch {
            // figure out how to track which character's delete button is pressed
        }
    }

    init {
        getList()
    }

    private fun getList() {
        uiScope.launch {
            _characterList.value = fetchCharacters()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}