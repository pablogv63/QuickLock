package com.pablogv63.quicklock.data.data_source.credential

import androidx.room.TypeConverter
import java.time.LocalDate

class CredentialConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun parsedStringToDate(dateString: String): LocalDate = LocalDate.parse(dateString)

        @TypeConverter
        @JvmStatic
        fun dateToParsedString(date: LocalDate): String = date.toString()
    }
}