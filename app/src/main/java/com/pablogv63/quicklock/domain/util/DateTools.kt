package com.pablogv63.quicklock.domain.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DateTools {

    fun LocalDate.timeSince(): String{
        val now = LocalDate.now()
        var tempDateTime = LocalDate.from(this)

        val years: Long = tempDateTime.until(now, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)
        if (years > 0) { return years.toString() + "y" }

        val months: Long = tempDateTime.until(now, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)
        if (months > 0) { return months.toString() + "m" }

        val weeks: Long = tempDateTime.until(now, ChronoUnit.WEEKS)
        tempDateTime = tempDateTime.plusWeeks(weeks)
        if (weeks > 0) { return weeks.toString() + "w" }

        val days: Long = tempDateTime.until(now, ChronoUnit.DAYS)
        if (days > 0) { return days.toString() + "d" }

        return "Today"
    }
}