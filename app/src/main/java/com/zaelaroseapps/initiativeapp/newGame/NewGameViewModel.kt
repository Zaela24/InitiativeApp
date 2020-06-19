// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        Log.i("NG", "OnAdd Reached")
        uiScope.launch {
            val newCharacter = Character()
            Log.i("NG", "OnAdd Coroutine Launched")
            addCharacter(newCharacter)
        }
    }
    private suspend fun addCharacter(character: Character) {
        Log.i("NG", "Add Character Reached")
        withContext(Dispatchers.IO) {
            Log.i("NG", "Add Character Coroutine Launched")
            database.addNewCharacter(character)
        }
        getList()  // Fetches updated character list and updates RecyclerView contents
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