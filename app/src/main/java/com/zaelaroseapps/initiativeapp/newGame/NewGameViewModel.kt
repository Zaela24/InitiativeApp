// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.database.CharacterDao
import kotlinx.coroutines.*

class NewGameViewModel(val database: CharacterDao) : ObservableViewModel() {

    /**
     * Allows cancelling of all ongoing Coroutines when onCleared() is called
     */
    private var viewModelJob = Job()

    /**
     * Defines Coroutine scope for all UI updates
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Used for manual incrementing of primary key for Character objects
     */
    private var pKey = 0

    /**
     * Encapsulated LiveData list to store list of currently available Characters
     */
    private var _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

    /**
     * Encapsulated LiveData to keep track of character selected by retaining the charID primary key
     */
    private var _characterClicked = MutableLiveData<Int>()
    val characterClicked: LiveData<Int>
        get() = _characterClicked

    /**
     * Accompanying public function to be used as part of clickListener to determine which Character
     * is selected by the user.
     */
    fun onCharacterClicked(id: Int) {
        Log.i("NGVM", "Clicked $id")
        _characterClicked.value = id
    }

    /**
     * Encapsulated LiveData to help record which group each Character belongs to.
     *
     * The three choices are determined by a choice from a dropdown spinner: Enemy, Ally, or Neutral
     */
    private var _team = MutableLiveData<String>()
    val team: LiveData<String>
        get() = _team

    /**
     * Encapsulated LiveData to keep track of whether or not a surprise round occurs
     */
    private var _surpriseRound = MutableLiveData<Boolean>()
    val surpriseRound: LiveData<Boolean>
        get() = _surpriseRound

    @Bindable
    fun getSurpriseRoundChecked() : Boolean {
        return when (val value = surpriseRound.value) {
            is Boolean -> value
            else -> {
                setSurpriseRoundChecked(false)
                false
            }
        }
    }
    fun setSurpriseRoundChecked(value: Boolean) {
        if (surpriseRound.value != value) {
            _surpriseRound.value = value
        }
    }

    fun onActsOnSurpriseChecked(id: Int) {

    }

    fun onInitiativeRollEdited(id: Int) {

    }

    fun onNameEdited(id: Int) {

    }

    private var _navigateToGame = MutableLiveData<Boolean>()
    val navigateToGame: LiveData<Boolean>
        get() = _navigateToGame

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

    fun onDelete() {
        uiScope.launch {
            // figure out how to track which character's delete button is pressed
        }
    }

    private suspend fun deleteCharacter(charID: Int) {
        withContext(Dispatchers.IO) {
            database.delete(charID)
        }
    }

    fun onAdd() {
        Log.i("NG", "OnAdd Reached")
        uiScope.launch {
            val newCharacter = Character()
            newCharacter.charID = pKey  // assign newly created Character a unique primary key
            Log.i("NG", "OnAdd Coroutine Launched")
            addCharacter(newCharacter)
        }
        pKey++  // increment primary key
    }
    private suspend fun addCharacter(character: Character) {
        Log.i("NG", "Add Character Reached")
        withContext(Dispatchers.IO) {
            Log.i("NG", "Add Character Coroutine Launched")
            database.addNewCharacter(character)
        }
        Log.i("NG", "CharID added: " + character.charID.toString())
        getList()  // Fetches updated character list and updates RecyclerView contents
    }

    private fun getList() {
        uiScope.launch {
            _characterList.value = fetchCharacters()
        }
    }

    private suspend fun fetchCharacters() : List<Character>? {
        var items: List<Character>? = null
        withContext(Dispatchers.IO) {
            items = database.reorder()
        }
        return items
    }


    init {
        clearList()
        getList()
    }

    private fun clearList() {
        uiScope.launch {
            clearTable()
        }
//        _characterList.value = null  // Empty local list after clearing table
        // Uncomment above if clear button is added
    }

    private suspend fun clearTable() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}