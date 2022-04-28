package com.joshowen.scrum_poker.ui.activities

import android.app.Application
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Log
import androidx.preference.PreferenceManager
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.utils.wrappers.PreferenceManagerWrapper.Companion.getIsAdvertisementsEnabled
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

interface Inputs {

}

interface Outputs {

}

@HiltViewModel
class MainActivityVM @Inject constructor(application: Application) : BaseViewModel(application),
    Inputs, Outputs {

    //region Variables

    val inputs : Inputs = this
    val outputs : Outputs = this

    //endregion

    init {

    }


    //endregion

    //region Inputs

    //endregion

    //region Output

    //endregion
}