package com.joshowen.scrum_poker.ui.fragments.collaboration.createlobby

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentCreateLobbyBinding
import com.joshowen.scrum_poker.types.enums.DeckType
import com.joshowen.scrum_poker.utils.RxExtensions.Companion.onClick
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


@AndroidEntryPoint
class CreateLobbyFragment : BaseFragment<FragmentCreateLobbyBinding>() {

    //region Variables
    private val viewModel : CreateLobbyFragmentVM by viewModels()

    //endregion

    //region BaseFragment Overrides

    override fun initViews() {
        super.initViews()
        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.inputs.onBackPress()
        }


    }

    override fun observeViewModel() {

        //region Inputs
        binding.tvDeckType.onClick()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.inputs.selectDeckTypeClick()
            }.autoDispose()
        //endregion

        //region Outputs
        viewModel.outputs.onBackPressed().subscribe {
            createAreYouSureDialog()
        }.autoDispose()

        viewModel.outputs.selectDeckTypeClicked().subscribe {
            AlertDialog.Builder(requireContext())
                .setTitle(resources.getString(R.string.alert_select_deck_title))
                .setItems(R.array.deck_type) { _, i ->
                    viewModel.inputs.selectDeck(DeckType.values()[i])
                }.create().show()
        }.autoDispose()

        viewModel.outputs.selectedDeck().subscribe { selectedDeckType ->
            binding.tvDeckType.text = resources.getString(selectedDeckType.resourceId)
        }.autoDispose()

        //endregion
    }


    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentCreateLobbyBinding {
        return FragmentCreateLobbyBinding.inflate(layoutInflater)
    }

    //endregion

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                viewModel.onBackPress()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createAreYouSureDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.dialog_cancel_lobby_body))
            .setPositiveButton(resources.getString(R.string.dialog_cancel_lobby_button_positive)) { _, _ ->
                findNavController().popBackStack()
            }
            .setNegativeButton(resources.getString(R.string.dialog_cancel_lobby_button_negative)) {_, _ -> }
            .create().show()
    }

    //region Options Menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
    //endregion
}