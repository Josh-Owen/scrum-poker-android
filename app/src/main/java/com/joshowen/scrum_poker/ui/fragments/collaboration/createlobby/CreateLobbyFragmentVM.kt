package com.joshowen.scrum_poker.ui.fragments.collaboration.createlobby

import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.types.enums.DeckType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

interface Inputs {
    fun onBackPress()
    fun selectDeckTypeClick()
    fun selectDeck(deckType: DeckType)
}

interface Outputs {
    fun onBackPressed() : Observable<Unit>
    fun selectDeckTypeClicked() : Observable<Unit>
    fun selectedDeck() : Observable<DeckType>
}

@HiltViewModel
class CreateLobbyFragmentVM @Inject constructor(): BaseViewModel(), Inputs, Outputs {

    //region Variables
    private val psBackPressed = PublishSubject.create<Unit>()
    private val psDeckTypeClicked = PublishSubject.create<Unit>()

    private val bsSelectedDeckType = BehaviorSubject.createDefault(DeckType.STANDARD)

    val inputs: Inputs = this
    val outputs: Outputs = this

    //endregion

    init {

    }

    //region Inputs
    override fun onBackPress() {
        psBackPressed.onNext(Unit)
    }

    override fun selectDeckTypeClick() {
        psDeckTypeClicked.onNext(Unit)
    }

    override fun selectDeck(deckType: DeckType) {
        bsSelectedDeckType.onNext(deckType)
    }

    //endregion

    //region Outputs
    override fun onBackPressed(): Observable<Unit> {
        return psBackPressed
    }

    override fun selectDeckTypeClicked(): Observable<Unit> {
        return psDeckTypeClicked
    }

    override fun selectedDeck(): Observable<DeckType> {
        return bsSelectedDeckType
    }

    //endregion


}