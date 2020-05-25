package com.zaelaroseapps.initiativeapp.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.databinding.CharacterListItemBinding
import com.zaelaroseapps.initiativeapp.databinding.GameFragmentBinding


class CharacterListAdapter(private val clickListener: CharacterClickListener) :
        ListAdapter<Character, CharacterListAdapter.CharacterViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.charID == newItem.charID
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.charID == newItem.charID
        }
    }

    class CharacterViewHolder(private var binding: CharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: CharacterClickListener, character: Character) {
            binding.character = character
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CharacterListItemBinding.inflate(layoutInflater, parent, false)
                return CharacterViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        return holder.bind(clickListener, getItem(position))
    }
}

class CharacterClickListener(val clickListener: (charID: Int) -> Unit) {
    fun onClick(character: Character) = clickListener(character.charID)
}