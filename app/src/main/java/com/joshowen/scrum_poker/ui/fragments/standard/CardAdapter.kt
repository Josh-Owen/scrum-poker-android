package com.joshowen.scrum_poker.ui.fragments.standard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshowen.scrum_poker.databinding.ItemCardBinding
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import com.joshowen.scrum_poker.utils.PreferenceManagerWrapper


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

            //itemView.drag

            tvCardContent.text = ""
            ivCardIcon.setImageDrawable(null)



            PreferenceManagerWrapper.getCardContentColour(itemView.context, itemView.resources)
                .let {
                    ivCardIcon.setColorFilter(it)
                    tvCardContent.setTextColor(it)
                }


            val cardBackgroundColour = if (card.cardType != CardType.COLOUR)
                PreferenceManagerWrapper.getCardBackgroundColour(
                    itemView.context,
                    itemView.resources
                )
            else
                ContextCompat.getColor(itemView.context, card.backgroundColourResourceId)

            cvContainer.setCardBackgroundColor(cardBackgroundColour)


            when (card.cardType) {
                CardType.ICON -> {
                    tvCardContent.visibility = View.GONE
                    card.resourceId?.let {
                        ivCardIcon.setImageResource(it)
                        ivCardIcon.visibility = View.VISIBLE
                    }
                }
                CardType.TEXT -> {
                    ivCardIcon.visibility = View.GONE
                    tvCardContent.text = card.value
                    tvCardContent.visibility = View.VISIBLE
                }
                CardType.COLOUR -> {
                    ivCardIcon.visibility = View.GONE
                    tvCardContent.visibility = View.GONE
                }
            }
            cvContainer.setOnClickListener(this)
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