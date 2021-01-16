package com.pablogv63.quicklock.credential

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