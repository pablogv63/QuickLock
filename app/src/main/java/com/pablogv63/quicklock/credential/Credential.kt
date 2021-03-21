package com.pablogv63.quicklock.credential

import com.pablogv63.quicklock.credential.fields.CredentialFields
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
class Credential (var name: String, var category: String = "None", var fields: CredentialFields = CredentialFields()){

    //Variables
    var lastViewed : String //Última vista
    var lastChanged : String //Último cambio
    var created: String //Creada

    //Expandida en la lista
    var expanded: Boolean = false

    //Posición en la lista general
    var positionInList: Int = -1

    init {
        val currentTime = Utilities.getCurrentDateTimeEncoded()
        created = currentTime
        lastViewed = currentTime
        lastChanged = currentTime
        CredentialCategories.addOrUpdateCategory(category)
    }

    fun updateChanged(){lastChanged = Utilities.getCurrentDateTimeEncoded()}

    fun updateViewed(){lastViewed = Utilities.getCurrentDateTimeEncoded()}

    //Constructor secundario: Leer desde archivo
    constructor(name: String, category: String,
                lastViewed: String, lastChanged: String, created: String,
                fields: CredentialFields) : this(name,category,fields) {
        this.created = created
        this.lastViewed = lastViewed
        this.lastChanged = lastChanged
    }

    //Sobrecarga de Equals
    override fun equals(other: Any?): Boolean {
        if (other is Credential) {
            return name == other.name
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "Name: $name, Category, $category, Fields: $fields"
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + fields.hashCode()
        result = 31 * result + lastViewed.hashCode()
        result = 31 * result + lastChanged.hashCode()
        result = 31 * result + expanded.hashCode()
        result = 31 * result + positionInList
        return result
    }

}