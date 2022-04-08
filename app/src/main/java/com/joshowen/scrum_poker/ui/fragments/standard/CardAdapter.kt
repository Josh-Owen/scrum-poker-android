package com.joshowen.scrum_poker.ui.fragments.standard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshowen.scrum_poker.databinding.ItemCardBinding
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType

class CardAdapter : ListAdapter<CardData, CardAdapter.CardViewHolder>(DiffCallback())  {

    //region Recycler View Overrides

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view : ItemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //endregion

    //region Diff Callbacks
    class DiffCallback : DiffUtil.ItemCallback<CardData>() {

        override fun areItemsTheSame(oldItem: CardData, newItem: CardData) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CardData, newItem: CardData) =
            oldItem.value == newItem.value
    }

    //endregion

    //region ViewHolders
    class CardViewHolder(binding : ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        private val tvCardContent = binding.tvCardValue
        private val cvContainer = binding.cvContainer
        private val ivCardIcon = binding.ivCardIcon

        fun bind(card: CardData) {

            tvCardContent.text = card.value

            cvContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, card.backgroundColourResourceId))

            if(card.cardType == CardType.ICON) {
                ivCardIcon.visibility = if(card.resourceId != null) View.VISIBLE else View.GONE
                ivCardIcon.setColorFilter(ContextCompat.getColor(itemView.context, card.cardContentResourceId))
                card.resourceId?.let {
                    ivCardIcon.setImageResource(it)
                }
            }
        }
    }

    //endregion
}