package com.joshowen.scrum_poker.ui.fragments.collaboration.collaboration

import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

interface Inputs {
    fun onBackPress()
    fun onSubmitButtonPress()
    fun lobbyId(lobbyId : String)
    fun setQuestionText(question : String)
}

interface Outputs {
    fun onBackPressed() : Observable<Unit>
    fun getSession() : Observable<OnlineSession>
    fun onSubmitButtonPressed() : Observable<Unit>
    fun isLobbyOwner() : Observable<Boolean>
    fun getQuestionText() : Observable<String>
}



data class User(val name : String, val data : CardData)

data class OnlineSession(val users : List<User>)


@HiltViewModel
class CollaborationFragmentVM @Inject constructor() : BaseViewModel(), Inputs, Outputs {

    //region Variables
    val inputs : Inputs = this
    val outputs : Outputs = this

    private val psBackPressed = PublishSubject.create<Unit>()

    private val psSubmitPressed = PublishSubject.create<Unit>()

    private val obsSession : Observable<OnlineSession>

    private val obsLobbyOwner : Observable<Boolean>

    private val bsQuestionContent = BehaviorSubject.createDefault("")

    private val bsLobbyId = BehaviorSubject.createDefault("")

    //endregion

    init {

        obsLobbyOwner = Observable.just(false)

        obsSession = Observable.just(
            OnlineSession(
            listOf(
                User("Jimmy",  CardData(CardType.TEXT, "2")),
                User("Terry",  CardData(CardType.TEXT, "2")),
                User("Kimmy",  CardData(CardType.TEXT, "2")),
                User("Jimmy",   CardData(
                    cardType = CardType.ICON,
                    backgroundColourResourceId = R.color.black,
                    resourceId = R.drawable.ic_baseline_color_lens_24,
                    cardContentResourceId = R.color.teal_200
                )),
                User("Barry",   CardData(
                    cardType = CardType.ICON,
                    backgroundColourResourceId = R.color.black,
                    resourceId = R.drawable.ic_baseline_color_lens_24,
                    cardContentResourceId = R.color.teal_200
                )),
                User("Mitchhhhhhhhhh",   CardData(
                    cardType = CardType.ICON,
                    backgroundColourResourceId = R.color.black,
                    resourceId = R.drawable.ic_settings,
                    cardContentResourceId = R.color.teal_200
                ))
            )
        )
        )

    }

    //region Inputs
    override fun onBackPress() {
        psBackPressed.onNext(Unit)
    }

    override fun onSubmitButtonPress() {
        psSubmitPressed.onNext(Unit)
    }

    override fun lobbyId(lobbyId: String) {
        bsLobbyId.onNext(lobbyId)
    }

    override fun setQuestionText(question: String) {
        bsQuestionContent.onNext(question)
    }

    //endregion

    //region Outputs
    override fun onBackPressed(): Observable<Unit> {
        return psBackPressed
    }

    override fun getSession(): Observable<OnlineSession> {
        return obsSession
    }

    override fun onSubmitButtonPressed(): Observable<Unit> {
        return psSubmitPressed
    }

    override fun isLobbyOwner(): Observable<Boolean> {
        return obsLobbyOwner
    }

    override fun getQuestionText(): Observable<String> {
        return bsQuestionContent
    }

    //endregion


}