package com.joshowen.scrum_poker.ui.activities

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseActivity
import com.joshowen.scrum_poker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    //region Variables

    private val viewModel : MainActivityVM by viewModels()

    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(setOf(R.id.nav_standard, R.id.nav_fibonacci, R.id.nav_hours, R.id.nav_risk), binding.drawerLayout)
    }

    //endregion

    //region BaseActivity Overrides

    override fun inflateBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setSupportActionBar(binding.toolbar)
        initialiseDrawerLayout()
    }


    override fun observeViewModel() {
        viewModel.outputs.loadAdvert()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                loadOrUnloadBannerAdvert(it)
            }.autoDispose()
    }

    //endregion

    //region Initialising-UI Features
    private fun initialiseDrawerLayout() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.nvPageContent.setupWithNavController(navController)
    }

    //endregion

    //region Life-Cycle Overrides
    override fun onDestroy() {
        super.onDestroy()
        binding.adView.destroy()
    }
    //endregion

    //endregion

    //region Navigation
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    //endregion

    //region Options Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                val navController = findNavController(R.id.fragmentContainerView)
                navController.navigate(R.id.nav_settings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //endregion

}