package com.pablogv63.quicklock.credential

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

    fun getList( ): MutableList<Credential> {return list
    }

    enum class FieldNames {
        PASSWORD,USERNAME,EMAIL
    }

}