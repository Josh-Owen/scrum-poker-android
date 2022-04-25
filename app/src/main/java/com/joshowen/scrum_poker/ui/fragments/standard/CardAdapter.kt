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

class CardAdapter(private val onClickCard: (cardData: CardData) -> Unit) :
    ListAdapter<CardData, CardAdapter.CardViewHolder>(DiffCallback()) {

    //region Recycler View Overrides
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: ItemCardBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(view, onClickCard)
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
    class CardViewHolder(binding: ItemCardBinding, val onClickCard: (cardData: CardData) -> Unit) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        //region Variables
        private val tvCardContent = binding.tvCardValue
        private val cvContainer = binding.cvContainer
        private val ivCardIcon = binding.ivCardIcon

        private var card: CardData? = null
        //endregion

        //region Bind
        fun bind(card: CardData) {

            this.card = card
            cvContainer.setOnClickListener(this)
            cvContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, card.backgroundColourResourceId))

            when(card.cardType) {
                CardType.ICON -> {
                    tvCardContent.visibility = View.GONE
                    card.resourceId?.let {
                        ivCardIcon.setImageResource(it)
                        ivCardIcon.setColorFilter(ContextCompat.getColor(itemView.context, card.cardContentResourceId))
                        ivCardIcon.visibility = View.VISIBLE
                    }
                }
                CardType.TEXT -> {
                    ivCardIcon.visibility = View.GONE
                    tvCardContent.text = card.value
                    tvCardContent.setTextColor(ContextCompat.getColor(itemView.context, card.cardContentResourceId))
                    tvCardContent.visibility = View.VISIBLE
                }
                CardType.COLOUR -> {
                    ivCardIcon.visibility = View.GONE
                    tvCardContent.visibility = View.GONE
                }
            }
        }
        //endregion

        //region View.OnClickListener
        override fun onClick(view: View) {
            card?.let {
                onClickCard(it)
            }
        }
        //endregion
    }

    //endregion
}