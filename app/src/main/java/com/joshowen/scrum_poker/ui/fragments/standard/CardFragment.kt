package com.joshowen.scrum_poker.ui.fragments.standard

import android.animation.ObjectAnimator
import android.app.backup.SharedPreferencesBackupHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentCardBinding
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import com.joshowen.scrum_poker.types.enums.DeckType
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
        binding.cvContainer.setCardBackgroundColor(ContextCompat.getColor(requireContext(), card.backgroundColourResourceId))

        when(card.cardType) {
            CardType.ICON -> {
                binding.tvCardValue.visibility = View.GONE
                card.resourceId?.let {
                    binding.ivCardIcon.setImageResource(it)
                    binding.ivCardIcon.setColorFilter(ContextCompat.getColor( requireContext(), card.cardContentResourceId))
                }
                binding.ivCardIcon.visibility =  View.VISIBLE
            }
            CardType.TEXT -> {
                binding.ivCardIcon.visibility = View.GONE
                binding.tvCardValue.setTextColor(ContextCompat.getColor(requireContext(), card.cardContentResourceId))
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