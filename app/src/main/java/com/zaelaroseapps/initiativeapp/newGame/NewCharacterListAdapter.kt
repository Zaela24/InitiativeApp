// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.databinding.NewCharacterListItemBinding

class NewCharacterListAdapter(val clickListener: NewCharacterClickListener):
    ListAdapter<Character, NewCharacterListAdapter.NewCharacterViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    class NewCharacterViewHolder(private var binding: NewCharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: NewCharacterClickListener, character: Character) {
            binding.character = character
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NewCharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewCharacterListItemBinding.inflate(layoutInflater, parent, false)
                return NewCharacterViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewCharacterViewHolder {
        return NewCharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewCharacterViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

class NewCharacterClickListener(val clickListener: (charID: Int) -> Unit) {
    fun onClick(character: Character) = clickListener(character.charID)
}