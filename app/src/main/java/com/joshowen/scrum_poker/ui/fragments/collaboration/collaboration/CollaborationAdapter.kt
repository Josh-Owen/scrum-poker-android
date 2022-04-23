package com.joshowen.scrum_poker.ui.fragments.collaboration.collaboration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshowen.scrum_poker.databinding.ItemCardOnlineBinding
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType

class CollaborationAdapter(private val onClickCard: (cardData: CardData) -> Unit) :
    ListAdapter<User, CollaborationAdapter.OnlineCardViewHolder>(DiffCallback()) {

    //region Recycler View Overrides
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnlineCardViewHolder {
        val view: ItemCardOnlineBinding =
            ItemCardOnlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnlineCardViewHolder(view, onClickCard)
    }

    override fun onBindViewHolder(holder: OnlineCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //endregion

    //region Diff Callbacks
    class DiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem.name == newItem.name
    }

    //endregion

    //region ViewHolders
    class OnlineCardViewHolder(binding: ItemCardOnlineBinding, val onClickCard: (cardData: CardData) -> Unit) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        //region Variables
        private val tvCardContent = binding.tvCardValue
        private val cvContainer = binding.cvContainer
        private val ivCardIcon = binding.ivCardIcon
        private val tvName = binding.tvName

        private var card: CardData? = null
        //endregion

        //region Bind
        fun bind(user : User) {

            this.card = user.data

            tvName.text = user.name

            cvContainer.setOnClickListener(this)
            cvContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.context, card!!.backgroundColourResourceId))

            if (card?.cardType == CardType.ICON) {
                tvCardContent.visibility = View.GONE
                card?.resourceId?.let {
                    ivCardIcon.setImageResource(it)
                    ivCardIcon.setColorFilter(ContextCompat.getColor(itemView.context, card!!.cardContentResourceId))
                    ivCardIcon.visibility = View.VISIBLE
                }
            } else if (card?.cardType == CardType.TEXT) {
                ivCardIcon.visibility = View.GONE
                tvCardContent.text = card?.value
                tvCardContent.setTextColor(ContextCompat.getColor(itemView.context, card!!.cardContentResourceId))
                tvCardContent.visibility = View.VISIBLE
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