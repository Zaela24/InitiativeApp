// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
class Character {
    @PrimaryKey(autoGenerate = true)
    //TODO(Implement work around for auto-gen + DELETE bug)
    var charID = 0

    @ColumnInfo(name = "side")
    var team: String = "Neutral"

    @ColumnInfo(name = "name")
    var name: String = "Name"

    @ColumnInfo(name = "hp")
    var hp = -1

    @ColumnInfo(name = "initiative_roll")
    var initiativeRoll = -1
}