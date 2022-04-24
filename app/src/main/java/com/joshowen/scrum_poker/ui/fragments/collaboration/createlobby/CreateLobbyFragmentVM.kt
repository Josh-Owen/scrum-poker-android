package com.joshowen.scrum_poker.ui.fragments.collaboration.createlobby

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
    fun selectDeckTypeClick()
    fun selectDeck(deckType: DeckType)
    fun inputName(name : String)
    fun createLobbyClick()
}

interface Outputs {
    fun onBackPressed() : Observable<Unit>
    fun selectDeckTypeClicked() : Observable<Unit>
    fun selectedDeck() : Observable<DeckType>
    fun getLobbyCode() : Observable<String>
    fun getLobbyCount() : Observable<Int>
    fun hasPassedValidation() : Observable<Boolean>
    fun getName() : Observable<String>
    fun createLobbyClicked() : Observable<Unit>
}

@HiltViewModel
class CreateLobbyFragmentVM @Inject constructor(val lobbyRepository: LobbyRepositoryImpl): BaseViewModel(), Inputs, Outputs {

    //region Variables
    private val psBackPressed = PublishSubject.create<Unit>()
    private val psDeckTypeClicked = PublishSubject.create<Unit>()

    private val psCreateLobbyClick = PublishSubject.create<Unit>()

    private val bsName = BehaviorSubject.createDefault("")

    private val obsLobbyCode : Observable<String>
    private val obsLobbyCount : Observable<Int>

    private val hasPassedValidation : Observable<Boolean>

    private val bsSelectedDeckType = BehaviorSubject.createDefault(DeckType.STANDARD)

    val inputs: Inputs = this
    val outputs: Outputs = this

    //endregion

    init {
        obsLobbyCode = Observable.just(generateRandomString(6).uppercase())
        obsLobbyCount = lobbyRepository.getLobbyMemberCount()
        hasPassedValidation = bsName.map { name -> name.isValidUserName() }
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

    override fun inputName(name: String) {
        bsName.onNext(name)
    }

    override fun createLobbyClick() {
        psCreateLobbyClick.onNext(Unit)
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

    override fun getLobbyCode(): Observable<String> {
        return obsLobbyCode
    }

    override fun getLobbyCount(): Observable<Int> {
        return obsLobbyCount
    }

    override fun hasPassedValidation(): Observable<Boolean> {
        return hasPassedValidation
    }

    override fun getName(): Observable<String> {
        return bsName
    }

    override fun createLobbyClicked(): Observable<Unit> {
        return psCreateLobbyClick
    }

    //endregion


}