package com.pablogv63.quicklock

import com.pablogv63.quicklock.Utilidades
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Clase que representa a una credencial
 * Contiene:
 * icon -> ruta del icono
 * name -> nombre personalizado
 * lastUsed -> fecha/hora de último uso
 */
class Credential (val iconSrc: Int, val name: String){

    //Variable que almacena la fecha/hora de último uso
    var lastUsed: String = Utilidades.getCurrentDateTimeEncoded() //TODO(Tras leer poner manualmente)
        //Sobreescribimos el método get para que devuelva la diferencia de tiempos
        get() {return Utilidades.getDateTimeDiff(field)}

}