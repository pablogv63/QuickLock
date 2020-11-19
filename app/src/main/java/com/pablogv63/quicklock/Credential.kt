package com.pablogv63.quicklock

import com.pablogv63.quicklock.Utilidades
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

/**
 * Clase que representa a una credencial
 * Contiene:
 * name -> nombre personalizado (Identificador)
 * lastAccessed -> fecha/hora de últimos (10) accesos
 * lastChanged -> fecha/hora de últimos (10) cambios
 * label -> etiqueta
 * campos -> otros campos
 */
class Credential (var name: String, var category: String = "None"){

    //Variables
    var lastViewed : String //Última vista
    var lastChanged : String //Último cambio

    init {
        val currentTime = Utilidades.getCurrentDateTimeEncoded()
        lastViewed = currentTime
        lastChanged = currentTime
        CredentialCategories.addOrUpdateCategory(category)
    }

    //Campos
    var fields = ArrayList<CredentialField>()

    //Constructor secundario: Leer desde archivo
    constructor(name: String, category: String,
                lastViewed: String, lastChanged: String,
                fields: ArrayList<CredentialField>) : this(name,category) {
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
open class CredentialField(val name: String, var value: String){}

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
}

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
    fun getCategories(name: String): MutableMap<String,Int> {
        return categories
    }

    //Borrar -> D
    fun deleteCategory(name: String) {
        categories.remove(name)
    }
}