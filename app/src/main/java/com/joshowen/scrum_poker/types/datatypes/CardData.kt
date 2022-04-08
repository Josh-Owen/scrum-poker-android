package com.joshowen.scrum_poker.types.datatypes

import com.joshowen.scrum_poker.R
import com.joshowen.scrum_poker.types.enums.CardType

data class CardData(val cardType : CardType, val value : String = "", val backgroundColourResourceId : Int = R.color.purple_700, val cardContentResourceId : Int= R.color.white, val resourceId : Int? = null)