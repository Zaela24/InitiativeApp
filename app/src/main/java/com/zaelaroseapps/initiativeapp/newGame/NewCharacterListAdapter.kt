// Copyright 2020, Zaela Rose

package com.zaelaroseapps.initiativeapp.newGame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zaelaroseapps.initiativeapp.database.Character
import com.zaelaroseapps.initiativeapp.databinding.AddCharacterListItemBinding
import com.zaelaroseapps.initiativeapp.databinding.NewCharacterListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM = 0
private const val FOOTER = 1

class NewCharacterListAdapter(val clickListener: NewCharacterClickListener):
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
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

    class AddCharacterViewHolder(private var binding: AddCharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): AddCharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AddCharacterListItemBinding.inflate(layoutInflater, parent, false)
                return AddCharacterViewHolder(binding)
            }
        }
    }

    private val adapterScope = CoroutineScope(Dispatchers.Default)
    fun addFooterAndSubmitList(list: List<Character>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Footer)
                else -> list.map { DataItem.CharacterItem(it) } + listOf(DataItem.Footer)
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> NewCharacterViewHolder.from(parent)
            FOOTER -> AddCharacterViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.CharacterItem -> ITEM
            is DataItem.Footer -> FOOTER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewCharacterViewHolder -> {
                val characterItem = getItem(position) as DataItem.CharacterItem
                holder.bind(clickListener, characterItem.character)
            }
        }
    }
}

class NewCharacterClickListener(val clickListener: (charID: Int) -> Unit) {
    fun onClick(character: Character) = clickListener(character.charID)
}

// sealed prevents other parts of code from altering:
sealed class DataItem {
    data class CharacterItem(val character: Character): DataItem() {
        override val id = character.charID
    }
    // object instead of class limits to 1 instance of header in this case
    object Footer: DataItem() {
        override val id = Int.MAX_VALUE // always places at bottom
    }

    abstract val id: Int
}