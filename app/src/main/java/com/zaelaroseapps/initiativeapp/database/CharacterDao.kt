// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CharacterDao {

    @Insert
    fun addNewCharacter(character: Character)

    @Update
    fun update(character: Character)

    @Query("SELECT * FROM game_table")
    fun getAll(): List<Character>

    @Query("SELECT * FROM game_table WHERE charID = :key")
    fun get(key: Int): Character?

    @Query("SELECT * FROM game_table ORDER BY initiative_roll DESC")
    fun reorder(): List<Character>

    @Query("DELETE FROM game_table")
    fun clear()

    @Query("DELETE FROM game_table WHERE charID = :key")
    fun delete(key: Int)
}