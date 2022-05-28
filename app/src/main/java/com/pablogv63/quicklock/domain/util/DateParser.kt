package com.pablogv63.quicklock.domain.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate

object DateParser {

    /**
     * Parses a string (dd/mm/yyyy) to LocalDate
     */
    fun String.toLocalDate(): LocalDate? {
        // Check string
        if (this.isEmpty()){
            return null
        }
        val dateTextParts = this.split("/")
        if (dateTextParts.size != 3){
            return null
        }

        val day = dateTextParts[0].toInt()
        val month = dateTextParts[1].toInt()
        val year = dateTextParts[2].toInt()
        return LocalDate.of(year,month,day)
    }

    fun LocalDate.toParsedDayMonthYearString(): String{
        val day = this.dayOfMonth
        val month = this.monthValue
        val year = this.year
        return "$day/$month/$year"
    }

    class LocalDateAdapter: TypeAdapter<LocalDate>() {
        override fun write(out: JsonWriter?, value: LocalDate?) {
            if (value == null) {
                out?.nullValue()
            } else {
                out?.value(value.toParsedDayMonthYearString())
            }
        }

        override fun read(jsonReader: JsonReader?): LocalDate? {
            return if (jsonReader?.peek() == JsonToken.NULL){
                jsonReader.nextNull()
                null
            } else {
                jsonReader?.nextString()?.toLocalDate()
            }
        }

    }
}