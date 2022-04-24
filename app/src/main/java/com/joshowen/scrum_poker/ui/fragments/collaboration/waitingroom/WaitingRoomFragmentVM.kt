package com.joshowen.scrum_poker.ui.fragments.collaboration.waitingroom

import com.joshowen.firebaserepository.repositories.LobbyRepositoryImpl
import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.types.enums.DeckType
import com.joshowen.scrum_poker.utils.generateRandomString
import com.joshowen.scrum_poker.utils.isValidUserName
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

interface Inputs {
    fun onBackPress()

}

interface Outputs {
    fun onBackPressed() : Observable<Unit>

}

@HiltViewModel
class WaitingRoomFragmentVM @Inject constructor(): BaseViewModel(), Inputs, Outputs {

    //region Variables
    private val psBackPressed = PublishSubject.create<Unit>()

    val inputs: Inputs = this
    val outputs: Outputs = this

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