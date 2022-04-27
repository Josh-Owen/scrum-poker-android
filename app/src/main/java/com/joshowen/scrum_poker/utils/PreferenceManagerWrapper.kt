package com.joshowen.scrum_poker.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.joshowen.scrum_poker.R


class PreferenceManagerWrapper {

    companion object {

        private fun getPreferenceManager(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun getCardPageBackgroundColour(context: Context, resources: Resources): Int? {
            val prefManager = getPreferenceManager(context)
            return if (prefManager.getBoolean(resources.getString(R.string.pref_custom_theme_key), false))
            {
                val resId =
                    prefManager.getInt(resources.getString(R.string.pref_background_key), -1)
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
            return if (prefManager.getBoolean(resources.getString(R.string.pref_custom_theme_key), false)) {
                getPreferenceManager(context).getInt(resources.getString(R.string.pref_card_background_key), ContextCompat.getColor(context, R.color.card_item_background_default))
            } else {
                ContextCompat.getColor(context, R.color.card_item_background_default)
            }
        }

        fun getCardContentColour(context: Context, resources: Resources): Int {
            val prefManager = getPreferenceManager(context)
            return if (prefManager.getBoolean(resources.getString(R.string.pref_custom_theme_key), false)) {
                getPreferenceManager(context).getInt(resources.getString(R.string.pref_card_content_colour_key), ContextCompat.getColor(context, R.color.card_item_content_colour_default))
            } else {
                ContextCompat.getColor(context, R.color.card_item_content_colour_default)
            }
        }
    }
}