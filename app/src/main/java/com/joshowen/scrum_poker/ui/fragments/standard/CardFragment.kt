package com.joshowen.scrum_poker.ui.fragments.standard

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentCardBinding
import com.joshowen.scrum_poker.types.enums.DeckType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CardFragment : BaseFragment<FragmentCardBinding>() {


    //region Variables

    private val viewModel : CardFragmentVM by viewModels()

    lateinit var deckType : DeckType

    private val cardAdapter : CardAdapter by lazy {
        CardAdapter()
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
        //endregion

        //region Outputs

        viewModel.outputs.getCardData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                cardAdapter.submitList(it)
            }.autoDispose()

        //endregion
    }

    override fun initArgs(arguments: Bundle) {
        super.initArgs(arguments)
        deckType = CardFragmentArgs.fromBundle(arguments).deckType
    }

    //endregion
}