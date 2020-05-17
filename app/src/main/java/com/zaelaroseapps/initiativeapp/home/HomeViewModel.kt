// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    /**
     * LiveData for HomeFragment buttons ; allows use of ClickListeners in xml
     *
     * Private Mutable versions with public non-Mutable versions to prevent other classes
     * from mutating the values
     */
    private val _navigateToNewGame = MutableLiveData<Boolean>()
    val navigateToNewGame: LiveData<Boolean>
        get() = _navigateToNewGame

    private val _navigateToLoad = MutableLiveData<Boolean>()
    val navigateToLoad: LiveData<Boolean>
        get() = _navigateToLoad

    private val _navigateToInstructions = MutableLiveData<Boolean>()
    val navigateToInstructions: LiveData<Boolean>
        get() = _navigateToInstructions

    /**
     * Click handlers
     */
    fun onNewClicked() {
        _navigateToNewGame.value = true
    }
    fun onLoadClicked() {
        _navigateToLoad.value = true
    }
    fun onHowToClicked() {
        _navigateToInstructions.value = true
    }

    /**
     * Methods to reset LiveData after navigating
     */
    fun onNavigatedToNew() {
        _navigateToNewGame.value = false
    }
    fun onNavigatedToLoaf() {
        _navigateToLoad.value = false
    }
    fun  onNavigatedToInstructions() {
        _navigateToInstructions.value = false
    }
}