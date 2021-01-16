package com.pablogv63.quicklock.credential

import com.pablogv63.quicklock.utilities.Utilities

/**
 * Clase que representa a una credencial
 * Contiene:
 * name -> nombre personalizado (Identificador)
 * lastAccessed -> fecha/hora de últimos (10) accesos
 * lastChanged -> fecha/hora de últimos (10) cambios
 * label -> etiqueta
 * campos -> otros campos
 */
class Credential (var name: String, var category: String = "None", var fields: MutableList<CredentialField> = mutableListOf()){

    //Variables
    var lastViewed : String //Última vista
    var lastChanged : String //Último cambio

    //Expandida en la lista
    var expanded: Boolean = false

    //Posición en la lista general
    var positionInList: Int = -1

    init {
        val currentTime = Utilities.getCurrentDateTimeEncoded()
        lastViewed = currentTime
        lastChanged = currentTime
        CredentialCategories.addOrUpdateCategory(category)
    }

    fun updateChanged(){lastChanged = Utilities.getCurrentDateTimeEncoded()}

    fun updateViewed(){lastViewed = Utilities.getCurrentDateTimeEncoded()}

    //Constructor secundario: Leer desde archivo
    constructor(name: String, category: String,
                lastViewed: String, lastChanged: String,
                fields: MutableList<CredentialField>) : this(name,category) {
        this.lastViewed = lastViewed
        this.lastChanged = lastChanged
    }

    //Constructor residual con icono
    constructor(icon: Int, name:String) : this(name,name){
        println("Bad constructor called")
    }

}

/**
 * Clase que representa a un campo de una credencial
 */
open class CredentialField(val id: Int, var value: String)