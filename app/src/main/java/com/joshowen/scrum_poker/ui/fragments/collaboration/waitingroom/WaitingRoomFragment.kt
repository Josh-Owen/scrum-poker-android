package com.joshowen.scrum_poker.ui.fragments.collaboration.waitingroom

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentWaitingRoomBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WaitingRoomFragment : BaseFragment<FragmentWaitingRoomBinding>() {

    //region Variables
    private val viewModel: WaitingRoomFragmentVM by viewModels()


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


        //endregion

        //region Outputs
        viewModel.outputs.onBackPressed().subscribe {

        }.autoDispose()


        //endregion
    }


    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentWaitingRoomBinding {
        return FragmentWaitingRoomBinding.inflate(layoutInflater)
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


    companion object {
        const val WAITING_ROOM_LOBBY_CODE_KEY = "lobbyCode"
    }
}