package com.joshowen.scrum_poker.ui.fragments.standard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentCardBinding
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import com.joshowen.scrum_poker.types.enums.DeckType
import com.joshowen.scrum_poker.utils.PreferenceManagerWrapper.Companion.getCardBackgroundColour
import com.joshowen.scrum_poker.utils.PreferenceManagerWrapper.Companion.getCardContentColour
import com.joshowen.scrum_poker.utils.PreferenceManagerWrapper.Companion.getCardPageBackgroundColour
import com.joshowen.scrum_poker.utils.onClick
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
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

        getCardPageBackgroundColour(requireContext(), resources)?.let {
            binding.mlParent.setBackgroundColor(it)
        }

        binding.rvCards.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = cardAdapter
        }
    }

    override fun observeViewModel() {


        //region Inputs
        viewModel.inputs.setCardType(deckType)

        binding.cvContainer.onClick().subscribe {
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
            binding.mlParent.transitionToStart()
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

        //region View Animation Logic
        binding.mlParent.transitionToEnd()
        //endregion

        //region Card Initialisation

        getCardContentColour(requireContext(), resources)?.let {
            binding.ivCardIcon.setColorFilter(it)
            binding.tvCardValue.setTextColor(it)
        }

        getCardBackgroundColour(requireContext(), resources)?.let {
            binding.cvContainer.setCardBackgroundColor(it)
        }


        when(card.cardType) {
            CardType.ICON -> {
                binding.tvCardValue.visibility = View.GONE
                card.resourceId?.let {
                    binding.ivCardIcon.setImageResource(it)
                }
                binding.ivCardIcon.visibility =  View.VISIBLE
            }
            CardType.TEXT -> {
                binding.ivCardIcon.visibility = View.GONE
                binding.tvCardValue.text = card.value
                binding.tvCardValue.visibility = View.VISIBLE
            }
            CardType.COLOUR -> {
                binding.ivCardIcon.visibility = View.GONE
                binding.tvCardValue.visibility = View.GONE
            }
        }
        //endregion
    }
    //endregion

}