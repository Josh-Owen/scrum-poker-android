package com.joshowen.scrum_poker.ui.fragments.settings

import com.joshowen.scrum_poker.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

interface Inputs {

}

interface Outputs {

}

@HiltViewModel
class SettingsFragmentVM @Inject constructor(): BaseViewModel(), Inputs, Outputs {

    //region Variables


    val inputs : Inputs = this
    val outputs : Outputs = this

    //endregion

    init {

    }

    //region Inputs

    //endregion

    //region Outputs

    //endregion


}