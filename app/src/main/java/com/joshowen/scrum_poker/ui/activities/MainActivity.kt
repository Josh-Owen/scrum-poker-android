package com.joshowen.scrum_poker.ui.activities

import android.view.LayoutInflater
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseActivity
import com.joshowen.scrum_poker.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    //region Variables

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(setOf(R.id.nav_standard, R.id.nav_fibonacci, R.id.nav_hours, R.id.nav_risk), binding.drawerLayout)
    }

    //endregion

    //region BaseActivity

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {

    }

    override fun initViews() {
        super.initViews()
        setSupportActionBar(binding.toolbar)
        initialiseDrawerLayout()
    }

    //endregion

    //region Initialising-UI Features
    private fun initialiseDrawerLayout() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    //endregion

    //region Navigation
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    //endregion
}