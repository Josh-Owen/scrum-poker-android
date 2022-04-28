package com.joshowen.scrum_poker.utils.wrappers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.ObbInfo
import android.content.res.Resources
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.joshowen.scrum_poker.ApplicationConfiguration
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.utils.extensions.loadAdvert
import com.joshowen.scrum_poker.utils.extensions.unLoadAdvert
import io.reactivex.rxjava3.core.Observable


class PreferenceManagerWrapper {

    companion object {

        fun getPreferenceManager(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun getIsAdvertisementsEnabled(context: Context, defaultValue : Boolean = true): Boolean {
            return getPreferenceManager(context)
                .getBoolean(
                    context.resources.getString(R.string.pref_advertisements_enabled_key),
                    defaultValue
                )
        }

        fun getIsDarkModeEnabled(context: Context, defaultValue : Boolean = true) : Boolean {
            return getPreferenceManager(context)
                .getBoolean(
                    context.resources.getString(R.string.pref_dark_mode_key),
                    defaultValue
                )
        }

        fun getCardPageBackgroundColour(context: Context): Int? {
            val prefManager = getPreferenceManager(context)
            return if (prefManager.getBoolean(
                    context.resources.getString(R.string.pref_custom_theme_key),
                    false
                )
            ) {
                val resId =
                    prefManager.getInt(context.resources.getString(R.string.pref_background_key), -1)
                if (resId != -1) {
                    resId
                } else
                    null
            } else {
                null
            }
        }

        fun getCardBackgroundColour(context: Context, resources: Resources): Int {
            val prefManager = getPreferenceManager(context)
            return if (prefManager.getBoolean(
                    resources.getString(R.string.pref_custom_theme_key),
                    false
                )
            ) {
                getPreferenceManager(context).getInt(
                    resources.getString(R.string.pref_card_background_key),
                    ContextCompat.getColor(context, R.color.card_item_background_default)
                )
            } else {
                ContextCompat.getColor(context, R.color.card_item_background_default)
            }
        }

        fun getCardContentColour(context: Context, resources: Resources): Int {
            val prefManager = getPreferenceManager(context)
            return if (prefManager.getBoolean(
                    resources.getString(R.string.pref_custom_theme_key),
                    false
                )
            ) {
                getPreferenceManager(context).getInt(
                    resources.getString(R.string.pref_card_content_colour_key),
                    ContextCompat.getColor(context, R.color.card_item_content_colour_default)
                )
            } else {
                ContextCompat.getColor(context, R.color.card_item_content_colour_default)
            }
        }
    }
}