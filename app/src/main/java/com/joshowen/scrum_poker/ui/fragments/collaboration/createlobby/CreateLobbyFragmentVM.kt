package com.joshowen.scrum_poker.ui.fragments.collaboration.createlobby

import com.joshowen.scrum_poker.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

interface Inputs {
    fun onBackPress()
}

interface Outputs {
    fun onBackPressed() : Observable<Unit>
}

@HiltViewModel
class CreateLobbyFragmentVM @Inject constructor(): BaseViewModel(), Inputs, Outputs {

    //region Variables
    private val psBackPressed = PublishSubject.create<Unit>()

    val inputs : Inputs = this
    val outputs : Outputs = this

    //endregion

    init {

    }

    //region Inputs
    override fun onBackPress() {
        psBackPressed.onNext(Unit)
    }
    //endregion

    //region Outputs
    override fun onBackPressed(): Observable<Unit> {
        return psBackPressed
    }
//endregion


}