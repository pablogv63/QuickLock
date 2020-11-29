package com.pablogv63.quicklock.utilities

import java.lang.Math.abs
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Utilidades {

    /*MANEJO DE FECHAS*/
    //Formato para las fechas
    val dateTimeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    /**
     * Obtiene la fecha y hora actuales y las codifica
     */
    fun getCurrentDateTimeEncoded(): String {
        //Declaramos la variable resultado
        var result: String

        //Obtenemos la fecha actual exacta
        val now = LocalDateTime.now()
        result = now.format(dateTimeFormat)

        //Devolvemos el texto resultado
        return result
    }

    /**
     * Obtiene la diferencia entre la fecha/hora actual (o de time) y la de last
     */
    fun getDateTimeDiff(last: String, time: String = getCurrentDateTimeEncoded()): String {

        //Aislamos la fecha actual y la anterior
        val last_split = last.split(" ")
        val now_split = time.split(" ")

        //Comprobamos diferencias -> Debería de tirar de strings.xml

        //Años, meses y días
        var singulares = arrayOf("día", "mes", "año")
        var plurales = arrayOf("días", "meses", "años")
        for (i in 2 downTo 0) {
            val now = now_split[0].split("-")[i].toInt()
            val last: Int = last_split[0].split("-")[i].toInt()
            //Comprobar si es distinto
            if (now != last) {
                var unidad = singulares[i]
                val diff = now - last
                if (diff > 1) unidad = plurales[i]
                return "hace $diff $unidad"
            }
        }
        //Semanas y días
        //Se queda pendiente

        //Horas, minutos y segundos
        singulares = arrayOf("hora", "minuto", "segundo")
        plurales = arrayOf("horas", "minutos", "segundos")
        for (i in 0..2) {
            val now = now_split[1].split(":")[i].toInt()
            val last: Int = last_split[1].split(":")[i].toInt()
            //Comprobar si es distinto
            if (now != last) {
                var unidad = singulares[i]
                val diff = abs(now - last)
                if (diff > 1) unidad = plurales[i]
                return "hace $diff $unidad"
            }
        }

        return "hace un momento"
    }
}