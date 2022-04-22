package com.joshowen.scrum_poker.ui.fragments.collaboration.onlinehome

import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.utils.isValidLobbyCode
import com.joshowen.scrum_poker.utils.isValidUserName
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

interface Inputs {
    fun clickCreateLobby()
    fun inputName(name : String)
    fun inputLobbyCode(code : String)
}

interface Outputs {
    fun createLobbyClicked() : Observable<Unit>
    fun getName() : Observable<String>
    fun getLobbyCode() : Observable<String>
    fun hasPassedValidation() : Observable<Boolean>
}

@HiltViewModel
class OnlineHomeFragmentVM @Inject constructor(): BaseViewModel(), Inputs, Outputs {

    //region Variables
    val inputs : Inputs = this
    val outputs : Outputs = this

    private val psClickCreateLobby = PublishSubject.create<Unit>()

    private val bsUserName = BehaviorSubject.createDefault("")

    private val bsLobbyCode = BehaviorSubject.createDefault("")

    private val obsHasPassedLobbyValidation : Observable<Boolean>

    //endregion

    init {
        obsHasPassedLobbyValidation = Observable.combineLatest(bsUserName, bsLobbyCode) { userName, lobbyCode ->
            Pair(userName, lobbyCode)
        }.map { (userName, lobbyCode) ->
            (userName.isValidUserName() && lobbyCode.isValidLobbyCode())
        }
           // .map { it.isValidUserName() && }
    }

    //region Inputs
    override fun clickCreateLobby() {
        psClickCreateLobby.onNext(Unit)
    }

    override fun inputName(name: String) {
        bsUserName.onNext(name)
    }

    override fun inputLobbyCode(code: String) {
        bsLobbyCode.onNext(code)
    }

    //endregion

    //region Outputs
    override fun createLobbyClicked(): Observable<Unit> {
        return psClickCreateLobby
    }

    override fun getName(): Observable<String> {
        return bsUserName
    }

    override fun getLobbyCode(): Observable<String> {
        return bsLobbyCode
    }

    override fun hasPassedValidation(): Observable<Boolean> {
        return obsHasPassedLobbyValidation
    }

    //endregion


}