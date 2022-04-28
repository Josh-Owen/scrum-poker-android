package com.joshowen.scrum_poker.base

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.AdView
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.utils.extensions.loadAdvert
import com.joshowen.scrum_poker.utils.extensions.unLoadAdvert
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


abstract class BaseActivity<Binding : ViewBinding> : AppCompatActivity(), OnSharedPreferenceChangeListener {

    //region Variables
    lateinit var binding: Binding

    private val compositeDisposable = CompositeDisposable()

    var adView : AdView?= null

    //endregion

    //region Activity Life-Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        intent?.extras?.let {
            intentExtras(it)
        }

        super.onCreate(savedInstanceState)
        binding = inflateBinding(layoutInflater)
        setContentView(binding.root)

        adView = binding.root.findViewById(R.id.adView)

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            applicationContext
        )
        prefs.registerOnSharedPreferenceChangeListener(this)

        if (prefs.getBoolean(resources.getString(R.string.pref_dark_mode_key), false)) {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        } else {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }

        if (prefs.getBoolean(resources.getString(R.string.pref_advertisements_enabled_key), false)) {
            adView?.loadAdvert()
        } else {
            adView?.unLoadAdvert()
        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initViews()

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    //endregion

    //region Rx
    fun Disposable.autoDispose() {
        compositeDisposable.add(this)
    }
    //endregion

    //region Core Abstract/Open functions To Override
    abstract fun inflateBinding(layoutInflater: LayoutInflater): Binding

    abstract fun observeViewModel()

    open fun intentExtras(args: Bundle) {}

    open fun initViews() {}

    //endregion

    //region OnSharedPreferenceChangeListener
    override fun onSharedPreferenceChanged(preferences : SharedPreferences, updatedFieldKey : String?) {
        val advertisementKey = resources.getString(R.string.pref_advertisements_enabled_key)
        if(updatedFieldKey == advertisementKey) {
            if(preferences.getBoolean(advertisementKey, false)) {
                adView?.loadAdvert()
            }
            else {
                adView?.unLoadAdvert()
            }
        }
    }
    //endregion
}