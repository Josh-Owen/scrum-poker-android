package com.joshowen.scrum_poker.types.datatypes

import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.types.enums.CardType

data class CardData(val cardType : CardType, val value : String = "", val backgroundColourResourceId : Int = R.color.card_item_background_default, val cardContentResourceId : Int= R.color.card_item_content_colour_default, val resourceId : Int? = null)