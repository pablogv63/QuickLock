package com.pablogv63.quicklock.data.data_source.credential

import androidx.room.TypeConverter
import java.time.LocalDate

class CredentialConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun parsedStringToDate(dateString: String): LocalDate? =
            if (dateString != "null") LocalDate.parse(dateString) else null

        @TypeConverter
        @JvmStatic
        fun dateToParsedString(date: LocalDate?): String =
            // toString on a nullable returns "null" if object is null
            date.toString()

        @TypeConverter
        @JvmStatic
        fun intToLong(int: Int): Long = int.toLong()

        @TypeConverter
        @JvmStatic
        fun longToInt(long: Long): Int = long.toInt()
    }
}