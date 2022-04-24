package com.joshowen.scrum_poker.ui.fragments.collaboration.collaboration

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentCollaborationBinding
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.ui.fragments.standard.CardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollaborationFragment : BaseFragment<FragmentCollaborationBinding>() {

    //region Variables
    private val viewModel : CollaborationFragmentVM by viewModels()

    private val cardAdapter : CollaborationAdapter by lazy {
        CollaborationAdapter(::onClickCard)
    }

    //endregion

    //region BaseFragment Overrides

    override fun initViews() {
        super.initViews()
        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.inputs.onBackPress()
        }

        binding.rvCards.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = cardAdapter
        }
    }

    override fun observeViewModel() {

        //region Inputs
        binding.btnSubmit.clicks().subscribe {
            viewModel.inputs.onSubmitButtonPress()
        }.autoDispose()

        binding.etTask.textChanges().subscribe {
            viewModel.inputs.lobbyId(it.toString())
        }.autoDispose()

        //endregion

        //region Outputs
        viewModel.outputs.onBackPressed().subscribe {
            displayCancelLobbyDialog()
        }.autoDispose()

        viewModel.outputs.onSubmitButtonPressed().subscribe {

        }.autoDispose()

        viewModel.outputs.getQuestionText().subscribe {
            if(it != binding.etTask.toString()) {
                binding.etTask.setText(it.toString())
              //  binding.tvTask.text = it.toString()
            }
        }.autoDispose()


        viewModel.outputs.getSession().subscribe {
            cardAdapter.submitList(it.users)
        }.autoDispose()

        viewModel.outputs.isLobbyOwner().subscribe {isOwner ->
            binding.tvTask.visibility = if(isOwner) View.INVISIBLE else View.VISIBLE
            binding.etTask.visibility = if(isOwner) View.VISIBLE else View.INVISIBLE
            binding.btnSubmit.visibility = if(isOwner) View.VISIBLE else View.GONE
        }.autoDispose()



        //endregion


    }

    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentCollaborationBinding {
       return FragmentCollaborationBinding.inflate(layoutInflater)
    }
    //endregion

    //region Adapter Callbacks
    private fun onClickCard(card : CardData) {


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
            .setTitle(resources.getString(R.string.dialog_exit_lobby_body))
            .setPositiveButton(resources.getString(R.string.dialog_cancel_lobby_button_positive)) { _, _ ->
                findNavController().popBackStack(R.id.nav_online, false)
            }
            .setNegativeButton(resources.getString(R.string.dialog_cancel_lobby_button_negative)) { _, _ -> }
            .create().show()
    }
    //endregion
}