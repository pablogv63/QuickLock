package com.pablogv63.quicklock.credential

import android.content.Context
import com.pablogv63.quicklock.credential.exceptions.DuplicateCredentialException
import com.pablogv63.quicklock.credential.exceptions.NotFoundCredentialException
import com.pablogv63.quicklock.utilities.FileHandler
import com.pablogv63.quicklock.utilities.Logger

/**
 * Objeto que almacena todas las credenciales
 */
object Credentials {
    //List
    private val list = mutableListOf<Credential>()

    //Exception messages
    private const val duplicateCredentialExceptionMessage = "Duplicate credential found: "
    private const val notFoundCredentialExceptionMessage = "Credential not found in list: "

    //Log messages
    private const val tag = "Credentials"

    //Filename to save
    private const val fileName = "Credentials.json"

    fun add(credential: Credential) {
        //Check if name already exists
        if (credential in list){
            throw DuplicateCredentialException(duplicateCredentialExceptionMessage + credential)
        }
        list.add(credential)
        Logger.d(tag, "Added credential to list ($credential)")
    }

    fun get(position: Int): Credential { return list[position] }

    fun update(credential: Credential) {
        val index = try {
            getIndex(credential)
        } catch (exception: NotFoundCredentialException) {
            throw exception
        }
        Logger.d(tag, "Updated credential: (${list[index]}) to ($credential)")
        list[index] = credential
    }

    fun delete(credential: Credential) {
        if (!list.remove(credential)){
            throw NotFoundCredentialException(notFoundCredentialExceptionMessage + credential)
        }
    }

    /**
     * Obtains the index. Throws NotFoundCredentialException if not found
     */
    private fun getIndex(credential: Credential): Int{
        //Check if exists
        val index: Int = list.indexOf(credential)
        if (index == -1){
            throw NotFoundCredentialException(notFoundCredentialExceptionMessage + credential)
        }
        return index
    }

    /**
     * Gets the list
     */
    fun getList(context: Context): MutableList<Credential> {
        if (list.isEmpty()){
            load(context)
        }
        return list
    }

    /**
     * Load the list from the filesystem
     */
    private fun load(context: Context) {
        val readList = FileHandler.readCredentialList(context, fileName)
        list.addAll(readList)
    }

    /**
     * Saves credential list to file
     */
    fun save(context: Context) {
        FileHandler.saveCredentialList(context, fileName, list)
    }

    private fun toJSON(pretty: Boolean = false){

    }

}