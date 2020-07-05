package com.test.basemodule.utils

import java.util.*

object CalendarUtils {
    enum class CalendarUnit(val value: Int) {
        YEAR(0),
        MONTH(1),
        DAY(2),
        HOUR(3),
        MINUTE(4)
    }
    @JvmStatic
    fun unixToCalendar(unixTime: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = unixTime * 1000
        return calendar
    }

    fun same(unit: CalendarUnit, c1: Calendar, c2: Calendar): Boolean {
        var sameUnit = true
        if (unit.value >= CalendarUnit.YEAR.value)
            sameUnit = sameUnit && c1[Calendar.YEAR] == c2[Calendar.YEAR]

        if (unit.value >= CalendarUnit.MONTH.value)
            sameUnit = sameUnit && c1[Calendar.MONTH] == c2[Calendar.MONTH]

        if (unit.value >= CalendarUnit.DAY.value)
            sameUnit = sameUnit && c1[Calendar.DAY_OF_MONTH] == c2[Calendar.DAY_OF_MONTH]

        if (unit.value >= CalendarUnit.HOUR.value)
            sameUnit = sameUnit && c1[Calendar.HOUR] == c2[Calendar.HOUR]

        if (unit.value >= CalendarUnit.MINUTE.value)
            sameUnit = sameUnit && c1[Calendar.MINUTE] == c2[Calendar.MINUTE]

        return sameUnit
    }
}