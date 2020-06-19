// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.zaelaroseapps.initiativeapp.database.Character

//import com.zaelaroseapps.initiativeapp.newGame.DataItem
//import com.zaelaroseapps.initiativeapp.newGame.NewCharacterListAdapter

@BindingAdapter("charIcon")
fun ImageView.setCharIcon(item: Character?) {
    item?.let {
        setImageResource(
            when (item.team) { // sets img based on user selection from spinner
                "Enemy" -> R.drawable.ic_monster_round
                "Ally" -> R.drawable.ic_hero_icon
                else -> R.drawable.ic_neutral_character_icon
            }
        )
    }
}