package com.joshowen.scrum_poker

import android.view.LayoutInflater
import com.joshowen.scrum_poker.base.BaseFragment
import com.joshowen.scrum_poker.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    //region BaseFragment
    override fun inflateBinding(layoutInflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {

    }

    override fun initViews() {
        super.initViews()
    }
    //endregion
}