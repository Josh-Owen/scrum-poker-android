package com.joshowen.scrum_poker.ui.fragments.standard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentCardBinding
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import com.joshowen.scrum_poker.types.enums.DeckType
import com.joshowen.scrum_poker.utils.RxExtensions.Companion.onClick
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CardFragment : BaseFragment<FragmentCardBinding>() {

    //region Variables

    private val viewModel : CardFragmentVM by viewModels()

    lateinit var deckType : DeckType

    private val cardAdapter : CardAdapter by lazy {
        CardAdapter(::onClickCard)
    }

    //endregion

    //region BaseFragment Overrides

    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentCardBinding {
        return FragmentCardBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()

        binding.rvCards.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = cardAdapter
        }
    }

    override fun observeViewModel() {

        //region Inputs
        viewModel.inputs.setCardType(deckType)

        binding.incSelectedCard.root.onClick().subscribe {
            viewModel.inputs.clickCard()
        }.autoDispose()

        //endregion

        //region Outputs

        viewModel.outputs.getCardData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                cardAdapter.submitList(it)
            }.autoDispose()

        viewModel.outputs.cardClicked().subscribe {
            binding.incSelectedCard.root.visibility = View.GONE
        }.autoDispose()

        //endregion
    }

    override fun initArgs(arguments: Bundle) {
        super.initArgs(arguments)
        deckType = CardFragmentArgs.fromBundle(arguments).deckType
    }

    //endregion

    //region Adapter Callbacks
    private fun onClickCard(card : CardData) {
        //Todo animation will happen here

        binding.incSelectedCard.cvContainer.setCardBackgroundColor(ContextCompat.getColor(requireContext(), card.backgroundColourResourceId))

        if(card.cardType == CardType.ICON) {
            binding.incSelectedCard.tvCardValue.visibility = View.GONE
            card.resourceId?.let {
                binding.incSelectedCard.ivCardIcon.setImageResource(it)
                binding.incSelectedCard.ivCardIcon.setColorFilter(ContextCompat.getColor( requireContext(), card.cardContentResourceId))
            }
            binding.incSelectedCard.ivCardIcon.visibility =  View.VISIBLE
        }
        else if(card.cardType == CardType.TEXT) {
            binding.incSelectedCard.ivCardIcon.visibility = View.GONE
            binding.incSelectedCard.tvCardValue.setTextColor(ContextCompat.getColor(requireContext(), card.cardContentResourceId))
            binding.incSelectedCard.tvCardValue.text = card.value
            binding.incSelectedCard.tvCardValue.visibility = View.VISIBLE
        }
        binding.incSelectedCard.root.visibility = View.VISIBLE
    }
    //endregion

}