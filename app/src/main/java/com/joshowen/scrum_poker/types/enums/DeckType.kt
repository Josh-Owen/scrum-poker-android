package com.joshowen.scrum_poker.types.enums

import com.joshowen.scrum_poker.R


enum class DeckType(val resourceId : Int) {
    STANDARD(R.string.menu_standard), FIBONACCI(R.string.menu_fibonacci), HOURS(R.string.menu_hours), RISK(R.string.menu_risk)
}