// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.newGame.NewCharacterListAdapter

@BindingAdapter("charIcon")
fun ImageView.setCharIcon(item: Character?) {
    item?.let {
        setImageResource(when (item.team) { // sets img based on user selection from spinner
            "Enemy" -> R.drawable.ic_monster_round
            "Ally" -> R.drawable.ic_hero_icon
            else -> R.drawable.ic_neutral_character_icon
        })
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Character>?) {
    val adapter = recyclerView.adapter as NewCharacterListAdapter
    adapter.submitList(data) {
        // scroll the list to the top after the diffs are calculated and posted
        recyclerView.scrollToPosition(0)
    }
}