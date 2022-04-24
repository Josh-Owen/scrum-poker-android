package com.joshowen.scrum_poker.ui.fragments.collaboration.onlinehome

import android.view.LayoutInflater
import android.view.Menu
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentOnlineHomeBinding
import com.joshowen.scrum_poker.ui.fragments.collaboration.waitingroom.WaitingRoomFragment.Companion.WAITING_ROOM_LOBBY_CODE_KEY
import com.joshowen.scrum_poker.utils.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnlineHomeFragment : BaseFragment<FragmentOnlineHomeBinding>() {

    //region Variables
    private val viewModel : OnlineHomeFragmentVM by viewModels()

    //endregion

    //region BaseFragment Overrides
    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentOnlineHomeBinding {
        return FragmentOnlineHomeBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setHasOptionsMenu(true)

    }

    override fun observeViewModel() {

        //region Inputs
        binding.btnCreateLobby.onClick().subscribe {
            viewModel.inputs.clickCreateLobby()
        }.autoDispose()

        binding.etNickName.textChanges().subscribe {
            viewModel.inputs.inputName(it.toString())
        }.autoDispose()

        binding.etLobbyCode.textChanges().subscribe {
            viewModel.inputs.inputLobbyCode(it.toString())
        }.autoDispose()

        binding.btnJoinLobby.clicks().subscribe {
            viewModel.inputs.joinLobbyClick()
        }.autoDispose()

        //endregion



        //region Outputs
        viewModel.outputs.createLobbyClicked().subscribe {
            findNavController().navigate(R.id.nav_create_lobby)
        }.autoDispose()

        viewModel.outputs.joinLobbyClicked().subscribe {
            findNavController().navigate(R.id.nav_waiting_room, bundleOf(Pair(WAITING_ROOM_LOBBY_CODE_KEY, it)))
        }.autoDispose()

        viewModel.outputs.getName().subscribe {
            if(it != binding.etNickName.text.toString()) {
                binding.etNickName.setText(it)
            }
        }.autoDispose()

        viewModel.outputs.getLobbyCode().subscribe {
            //Todo: Replace this with a custom RX Extension
            if(it != binding.etLobbyCode.text.toString()) {
                binding.etLobbyCode.setText(it)
            }
        }

        viewModel.outputs.hasPassedValidation().subscribe {

            binding.btnJoinLobby.isEnabled = it
        }.autoDispose()
        //endregion

    }
    //endregion

    //region Options Menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }
    //endregion

}