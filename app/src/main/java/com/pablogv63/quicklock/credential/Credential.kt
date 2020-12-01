package com.pablogv63.quicklock

import com.pablogv63.quicklock.utilities.Utilidades

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
        val currentTime = Utilidades.getCurrentDateTimeEncoded()
        lastViewed = currentTime
        lastChanged = currentTime
        CredentialCategories.addOrUpdateCategory(category)
    }

    fun updateChanged(){lastChanged = Utilidades.getCurrentDateTimeEncoded()}

    fun updateViewed(){lastViewed = Utilidades.getCurrentDateTimeEncoded()}

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
 * Objeto que almacena todas las credenciales
 */
object Credentials {
    private val list = mutableListOf<Credential>()
    init {
        //TODO(Retrieve credentials from file
    }

    fun add(credential: Credential) {
        credential.positionInList = list.size
        list.add(credential)
    }

    fun get(position: Int): Credential { return list[position] }

    fun update(credential: Credential) { list[credential.positionInList] = credential }

    fun getList( ): MutableList<Credential> {return list}

    enum class FieldNames {
        PASSWORD,USERNAME,EMAIL
    }

}

/**
 * Clase que representa a un campo de una credencial
 */
open class CredentialField(val id: Int, var value: String)

/**
 * Objeto que almacena las categorías
 */
object CredentialCategories {
    //Diccionario de categorias
    private val categories = mutableMapOf<String, Int>()

    //Añadir o actualizar -> C, U
    fun addOrUpdateCategory(key: String) {
        val counter = categories[key]?:0
        categories[key] = counter+1
    }

    //GET de todas-> R
    fun getCategories(): MutableMap<String,Int> {
        return categories
    }
    fun getCategoriesAsText(): MutableList<String> {
        var array = mutableListOf<String>()
        categories.forEach() { entry: Map.Entry<String, Int> ->
            array.add(entry.key)
        }
        return array
    }

    //Borrar -> D
    fun deleteCategory(name: String) {
        categories.remove(name)
    }
}