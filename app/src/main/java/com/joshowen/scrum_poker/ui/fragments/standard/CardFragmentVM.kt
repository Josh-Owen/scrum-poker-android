package com.joshowen.scrum_poker.ui.fragments.standard

import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.base.BaseViewModel
import com.joshowen.scrum_poker.types.datatypes.CardData
import com.joshowen.scrum_poker.types.enums.CardType
import com.joshowen.scrum_poker.types.enums.DeckType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

interface Input {
    fun setCardType(deckType: DeckType)
    fun clickCard()
}

interface Output {
    fun getCardData(): Observable<List<CardData>>
    fun cardClicked() : Observable<Unit>
}

@HiltViewModel
class CardFragmentVM @Inject constructor() : BaseViewModel(), Input, Output {


    //region Variables

    val inputs: Input = this
    val outputs: Output = this

    private val bsCardType = BehaviorSubject.create<DeckType>()
    private val psCardClicked = PublishSubject.create<Unit>()

    private val obsCardInformation: Observable<List<CardData>> = bsCardType.flatMap { cardType ->
        Observable.create { emitter ->

            val output: List<CardData> = when (cardType) {
                DeckType.STANDARD -> listOf(
                    CardData(
                        CardType.TEXT, "0"
                    ),
                    CardData(CardType.TEXT, "1/2"),
                    CardData(CardType.TEXT, "1"),
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
                        resourceId = R.drawable.ic_coffee_mug
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
                    CardData(
                        cardType = CardType.ICON,
                        resourceId = R.drawable.ic_coffee_mug,
                    ),
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
                    CardData(
                        cardType = CardType.ICON,
                        resourceId = R.drawable.ic_coffee_mug,
                    ),
                )
                DeckType.RISK -> listOf(
                    CardData(CardType.COLOUR, backgroundColourResourceId = R.color.card_colour_green),
                    CardData(CardType.COLOUR, backgroundColourResourceId = R.color.card_colour_brown),
                    CardData(CardType.COLOUR, backgroundColourResourceId = R.color.card_colour_orange),
                    CardData(CardType.COLOUR, backgroundColourResourceId = R.color.card_colour_purple),
                    CardData(CardType.COLOUR, backgroundColourResourceId = R.color.card_colour_red),
                    CardData(CardType.TEXT, "∞"),
                    CardData(CardType.TEXT, "?"),
                    CardData(
                        cardType = CardType.ICON,
                        resourceId = R.drawable.ic_coffee_mug,
                    ),
                )
                else -> listOf()
            }
            emitter.onNext(output)
        }
    }

    //endregion

    //region Inputs
    override fun setCardType(deckType: DeckType) {
        bsCardType.onNext(deckType)
    }

    override fun clickCard() {
        psCardClicked.onNext(Unit)
    }

    //endregion

    //region Output
    override fun getCardData(): Observable<List<CardData>> {
        return obsCardInformation
    }

    override fun cardClicked(): Observable<Unit> {
        return psCardClicked
    }
    //endregion
}