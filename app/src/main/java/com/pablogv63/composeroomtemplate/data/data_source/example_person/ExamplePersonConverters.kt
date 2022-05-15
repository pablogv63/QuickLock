package com.pablogv63.composeroomtemplate.data.data_source.example_person

import androidx.room.TypeConverter
import java.time.LocalDate

class ExamplePersonConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun parsedStringToDate(dateString: String): LocalDate = LocalDate.parse(dateString)

        @TypeConverter
        @JvmStatic
        fun dateToParsedString(date: LocalDate): String = date.toString()
    }
}