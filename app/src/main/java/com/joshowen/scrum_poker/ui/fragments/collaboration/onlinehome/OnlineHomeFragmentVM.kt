package com.joshowen.scrum_poker.ui.fragments.collaboration.onlinehome

import com.joshowen.scrum_poker.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

interface Inputs {
    fun clickCreateLobby()
}

interface Outputs {
    fun createLobbyClicked() : Observable<Unit>
}

@HiltViewModel
class OnlineHomeFragmentVM @Inject constructor(): BaseViewModel(), Inputs, Outputs {

    //region Variables
    private val psClickCreateLobby = PublishSubject.create<Unit>()

    val inputs : Inputs = this
    val outputs : Outputs = this

    //endregion

    init {

    }

    //region Inputs
    override fun clickCreateLobby() {
        psClickCreateLobby.onNext(Unit)
    }
    //endregion

    //region Outputs
    override fun createLobbyClicked(): Observable<Unit> {
        return psClickCreateLobby
    }
//endregion


}