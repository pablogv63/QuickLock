package com.pablogv63.quicklock.domain.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

object DateTools {

    fun LocalDate.timeSince(): String{
        var yearText = "y"
        var monthText = "m"
        var weekText = "w"
        var dayText = "d"
        var todayText = "Today"
        when(Locale.getDefault()){
            // Not ideal but functional
            Locale("es", "ES") -> {
                yearText = "a"
                monthText = "m"
                weekText = "s"
                dayText = "d"
                todayText = "Hoy"
            }
        }


        val now = LocalDate.now()
        var tempDateTime = LocalDate.from(this)

        val years: Long = tempDateTime.until(now, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)
        if (years > 0) { return years.toString() + yearText }

        val months: Long = tempDateTime.until(now, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)
        if (months > 0) { return months.toString() + monthText }

        val weeks: Long = tempDateTime.until(now, ChronoUnit.WEEKS)
        tempDateTime = tempDateTime.plusWeeks(weeks)
        if (weeks > 0) { return weeks.toString() + weekText }

        val days: Long = tempDateTime.until(now, ChronoUnit.DAYS)
        if (days > 0) { return days.toString() + dayText }

        return todayText
    }
}