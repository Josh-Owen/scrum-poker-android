package com.joshowen.scrum_poker.ui.fragments.standard

import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import com.joshowen.scrum_poker.types.enums.DeckType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

interface CardFragmentVMInput {
    fun setCardType(deckType: DeckType)
}

interface CardFragmentVMOutput {
    fun getCardData(): Observable<List<CardData>>
}

@HiltViewModel
class CardFragmentVM @Inject constructor() : BaseViewModel(), CardFragmentVMInput,
    CardFragmentVMOutput {


    //region Variables

    val inputs: CardFragmentVMInput = this
    val outputs: CardFragmentVMOutput = this

    private val psCardType = BehaviorSubject.create<DeckType>()

    private val obsCardInformation: Observable<List<CardData>>


    init {
        obsCardInformation = psCardType.flatMap { cardType ->
            Observable.create { emitter ->

                val output: List<CardData> = when (cardType) {
                    DeckType.STANDARD -> listOf(
                        CardData(
                            CardType.TEXT, "0",
                            backgroundColourResourceId = R.color.black
                        ),
                        CardData(CardType.TEXT, "1/2"),
                        CardData(
                            CardType.TEXT, "1",
                            cardContentResourceId = R.color.teal_200
                        ),
                        CardData(CardType.TEXT, "2"),
                        CardData(CardType.TEXT, "3"),
                        CardData(CardType.TEXT, "5"),
                        CardData(CardType.TEXT, "8"),
                        CardData(CardType.TEXT, "13"),
                        CardData(CardType.TEXT, "20"),
                        CardData(CardType.TEXT, "40"),
                        CardData(CardType.TEXT, "100"),
                        CardData(CardType.TEXT, "∞"),
                        CardData(CardType.TEXT, "?"),
                        CardData(
                            cardType = CardType.ICON,
                            backgroundColourResourceId = R.color.black,
                            resourceId = R.drawable.ic_baseline_color_lens_24,
                            cardContentResourceId = R.color.teal_200
                        ),
                    )
                    DeckType.FIBONACCI -> listOf(
                        CardData(CardType.TEXT, "0"),
                        CardData(CardType.TEXT, "1"),
                        CardData(CardType.TEXT, "2"),
                        CardData(CardType.TEXT, "3"),
                        CardData(CardType.TEXT, "5"),
                        CardData(CardType.TEXT, "8"),
                        CardData(CardType.TEXT, "13"),
                        CardData(CardType.TEXT, "21"),
                        CardData(CardType.TEXT, "34"),
                        CardData(CardType.TEXT, "55"),
                        CardData(CardType.TEXT, "89"),
                        CardData(CardType.TEXT, "144"),
                        CardData(CardType.TEXT, "∞"),
                        CardData(CardType.TEXT, "?"),
                        CardData(CardType.TEXT, "C"),
                    )
                    DeckType.HOURS -> listOf(
                        CardData(CardType.TEXT, "0"),
                        CardData(CardType.TEXT, "1"),
                        CardData(CardType.TEXT, "2"),
                        CardData(CardType.TEXT, "3"),
                        CardData(CardType.TEXT, "4"),
                        CardData(CardType.TEXT, "6"),
                        CardData(CardType.TEXT, "8"),
                        CardData(CardType.TEXT, "12"),
                        CardData(CardType.TEXT, "16"),
                        CardData(CardType.TEXT, "24"),
                        CardData(CardType.TEXT, "32"),
                        CardData(CardType.TEXT, "40"),
                        CardData(CardType.TEXT, "∞"),
                        CardData(CardType.TEXT, "?"),
                        CardData(CardType.TEXT, "C"),
                    )
                    DeckType.RISK -> listOf(
                        CardData(CardType.TEXT, "G"),
                        CardData(CardType.TEXT, "B"),
                        CardData(CardType.TEXT, "O"),
                        CardData(CardType.TEXT, "P"),
                        CardData(CardType.TEXT, "R"),
                        CardData(CardType.TEXT, "∞"),
                        CardData(CardType.TEXT, "?"),
                        CardData(CardType.TEXT, "C"),
                    ) // Todo: Need to add colours / images and such in here for the view types
                    else -> listOf()
                }
                emitter.onNext(output)
            }
        }
    }

    //endregion

    //region Inputs
    override fun setCardType(deckType: DeckType) {
        psCardType.onNext(deckType)
    }
    //endregion

    //region Output
    override fun getCardData(): Observable<List<CardData>> {
        return obsCardInformation
    }
    //endregion
}