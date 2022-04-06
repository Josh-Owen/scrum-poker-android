package com.joshowen.scrum_poker.ui.fragments.standard

import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

interface CardFragmentVMInput {
    fun setCardType(cardType : CardType)
}

interface CardFragmentVMOutput {
    fun getCardData() : Observable<List<CardData>>
}

@HiltViewModel
class CardFragmentVM @Inject constructor(): BaseViewModel(), CardFragmentVMInput, CardFragmentVMOutput{



    //region Variables

    val inputs : CardFragmentVMInput = this
    val outputs : CardFragmentVMOutput = this

    private val psCardType = BehaviorSubject.create<CardType>()

    private val obsCardInformation : Observable<List<CardData>>


    init {
        obsCardInformation = psCardType.flatMap { cardType ->
            Observable.create { emitter ->

                val output: List<CardData> = when (cardType) {
                    CardType.STANDARD -> listOf(
                        CardData(0, "0"),
                        CardData(0, "1/2"),
                        CardData(0, "1"),
                        CardData(0, "2"),
                        CardData(0, "3"),
                        CardData(0, "5"),
                        CardData(0, "8"),
                        CardData(0, "13"),
                        CardData(0, "20"),
                        CardData(0, "40"),
                        CardData(0, "100"),
                        CardData(0, "∞"),
                        CardData(0, "?"),
                        CardData(0, "C"),
                    )
                    CardType.FIBONACCI -> listOf(
                        CardData(0, "0"),
                        CardData(0, "1"),
                        CardData(0, "2"),
                        CardData(0, "3"),
                        CardData(0, "5"),
                        CardData(0, "8"),
                        CardData(0, "13"),
                        CardData(0, "21"),
                        CardData(0, "34"),
                        CardData(0, "55"),
                        CardData(0, "89"),
                        CardData(0, "144"),
                        CardData(0, "∞"),
                        CardData(0, "?"),
                        CardData(0, "C"),
                    )
                    CardType.HOURS -> listOf(
                        CardData(0, "0"),
                        CardData(0, "1"),
                        CardData(0, "2"),
                        CardData(0, "3"),
                        CardData(0, "4"),
                        CardData(0, "6"),
                        CardData(0, "8"),
                        CardData(0, "12"),
                        CardData(0, "16"),
                        CardData(0, "24"),
                        CardData(0, "32"),
                        CardData(0, "40"),
                        CardData(0, "∞"),
                        CardData(0, "?"),
                        CardData(0, "C"),
                    )
                    CardType.RISK -> listOf(
                        CardData(0, "G"),
                        CardData(0, "B"),
                        CardData(0, "O"),
                        CardData(0, "P"),
                        CardData(0, "R"),
                        CardData(0, "∞"),
                        CardData(0, "?"),
                        CardData(0, "C"),
                        ) // Todo: Need to add colours / images and such in here for the view types
                    else -> listOf()
                }
                emitter.onNext(output)
            }
        }
    }

    //endregion

    //region Inputs
    override fun setCardType(cardType: CardType) {
        psCardType.onNext(cardType)
    }
    //endregion

    //region Output
    override fun getCardData(): Observable<List<CardData>> {
        return obsCardInformation
    }
    //endregion
}