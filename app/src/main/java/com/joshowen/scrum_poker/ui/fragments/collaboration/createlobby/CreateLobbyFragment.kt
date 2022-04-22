package com.joshowen.scrum_poker.ui.fragments.collaboration.createlobby

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.widget.textChanges
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
    private val viewModel: CreateLobbyFragmentVM by viewModels()

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

        binding.etNickName.textChanges().subscribe {
            //Todo: Replace this with a custom RX Extension
            viewModel.inputs.inputName(it.toString())
        }.autoDispose()

        //endregion

        //region Outputs
        viewModel.outputs.onBackPressed().subscribe {
            displayCancelLobbyDialog()
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

        viewModel.outputs.getLobbyCode().subscribe {
            binding.tvLobbyCode.text = it
        }.autoDispose()

        viewModel.outputs.getLobbyCount().subscribe { userCount ->
            binding.tvUsersInLobby.text = if (userCount == 1) String.format(
                resources.getString(R.string.create_lobby_number_of_users_single),
                userCount
            )
            else
                String.format(
                    resources.getString(R.string.create_lobby_number_of_users_multiple),
                    userCount
                )
        }.autoDispose()

        viewModel.outputs.hasPassedValidation().subscribe {
            binding.btnStart.isEnabled = it
        }.autoDispose()

        viewModel.getName().subscribe {
            //Todo: Replace this with custom RX function
            if (it.toString() != binding.etNickName.text.toString()) {
                binding.etNickName.setText(it)
            }
        }.autoDispose()

        //endregion
    }


    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentCreateLobbyBinding {
        return FragmentCreateLobbyBinding.inflate(layoutInflater)
    }

    //endregion

    //region Options Menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                viewModel.onBackPress()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

    //region AlertDialogs
    private fun displayCancelLobbyDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.dialog_cancel_lobby_body))
            .setPositiveButton(resources.getString(R.string.dialog_cancel_lobby_button_positive)) { _, _ ->
                findNavController().popBackStack()
            }
            .setNegativeButton(resources.getString(R.string.dialog_cancel_lobby_button_negative)) { _, _ -> }
            .create().show()
    }
    //endregion
}