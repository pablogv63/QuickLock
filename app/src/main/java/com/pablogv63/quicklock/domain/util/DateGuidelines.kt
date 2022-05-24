package com.pablogv63.quicklock.domain.util

import com.pablogv63.quicklock.domain.util.DateParser.toLocalDate
import java.time.LocalDate

object DateGuidelines {
    private const val MIN_EXPIRE_DAYS: Long = 30
    private const val MIN_MODIFIED_RECOMMENDED: Long = 6

    fun LocalDate.expiresSoon(): Boolean{
        return this.plusDays(MIN_EXPIRE_DAYS).isAfter(LocalDate.now())
    }

    fun String.expiresSoon(): Boolean{
        return this.toLocalDate()?.plusDays(MIN_EXPIRE_DAYS)?.isAfter(LocalDate.now()) ?: false
    }

    fun LocalDate.shouldBeUpdated(): Boolean{
        return this.plusMonths(MIN_MODIFIED_RECOMMENDED).isBefore(LocalDate.now())
    }

    fun String.shouldBeUpdated(): Boolean{
        return this.toLocalDate()?.plusMonths(MIN_MODIFIED_RECOMMENDED)?.isBefore(LocalDate.now())
            ?: false
    }
}