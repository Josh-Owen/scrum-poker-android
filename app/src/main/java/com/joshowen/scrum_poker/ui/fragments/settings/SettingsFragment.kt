package com.joshowen.scrum_poker.ui.fragments.settings

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.preference.SwitchPreference
import com.github.koston.preference.ColorPreferenceFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.joshowen.scrum_poker.R


class SettingsFragment : ColorPreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)


        val themeMode: SwitchPreference? = findPreference(resources.getString(R.string.pref_dark_mode_key))
        themeMode?.setOnPreferenceChangeListener { _, _ ->
            MaterialAlertDialogBuilder(requireContext()).setMessage(
               R.string.settings_switch_theme_warning_body
            ).setPositiveButton(
               R.string.settings_switch_theme_warning_positive
            ) { _, _: Int ->
                val intent: Intent? = requireContext().packageManager.getLaunchIntentForPackage(requireContext().applicationContext.packageName)
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }.setNegativeButton(
               R.string.settings_switch_theme_warning_negative
            ) { _, _: Int ->
                themeMode.isChecked = !themeMode.isChecked
            }
                .setOnCancelListener { themeMode.isChecked = !themeMode.isChecked }
                .setTitle(R.string.settings_switch_theme_warning_title).show()
            true
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

}