package com.joshowen.scrum_poker.ui.fragments.settings

import android.view.LayoutInflater
import android.view.Menu
import androidx.core.view.iterator
import androidx.fragment.app.viewModels
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    //region Variables
    private val viewModel : SettingsFragmentVM by viewModels()

    //endregion

    //region BaseFragment Overrides

    override fun initViews() {
        super.initViews()
        setHasOptionsMenu(true)
    }

    override fun observeViewModel() {

        //region Inputs

        //endregion

        //region Outputs

        //endregion
    }


    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    //endregion

    //region Options Menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    //endregion
}