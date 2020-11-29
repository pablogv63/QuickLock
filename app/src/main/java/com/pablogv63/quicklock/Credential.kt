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

    init {
        val currentTime = Utilidades.getCurrentDateTimeEncoded()
        lastViewed = currentTime
        lastChanged = currentTime
        CredentialCategories.addOrUpdateCategory(category)
    }

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
    val list = mutableListOf<Credential>()
    init {
        //TODO(Retrieve credentials from file
    }

    fun add(credential: Credential) {
        list.add(credential)
    }

    enum class FieldNames {
        PASSWORD,USERNAME,EMAIL
    }

}

/**
 * Clase que representa a un campo de una credencial
 */
open class CredentialField(val id: Int, var value: String){}

/*
class CredentialPassword(name: String = "password", value: String) : CredentialField(name, value) {

    init {
        //Manipulamos la contraseña para hashearla
        //Si se hashea una contraseña custom no se podrá recuperar
        //this.value = value.sha256()
    }

    //Extendemos localmente la clase String para añadirle hasheo a sha256
    fun String.sha256(): String{
        val bytes = this.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", {str, it -> str + "%02x".format(it)})
    }
}*/

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
            array.add(entry.key + " (" + entry.value + ")")
        }
        return array
    }

    //Borrar -> D
    fun deleteCategory(name: String) {
        categories.remove(name)
    }
}