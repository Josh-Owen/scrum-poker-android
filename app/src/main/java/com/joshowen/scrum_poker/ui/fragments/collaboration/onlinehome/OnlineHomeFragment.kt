package com.joshowen.scrum_poker.ui.fragments.collaboration.onlinehome

import android.view.LayoutInflater
import android.view.Menu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentOnlineHomeBinding
import com.joshowen.scrum_poker.utils.RxExtensions.Companion.onClick
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
        //endregion
        viewModel.outputs.createLobbyClicked().subscribe {
            findNavController().navigate(R.id.nav_create_lobby)
        }.autoDispose()



        //region Outputs

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