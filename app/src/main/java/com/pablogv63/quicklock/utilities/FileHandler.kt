package com.pablogv63.quicklock.utilities


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.pablogv63.quicklock.credential.Credential
import java.io.File
import java.io.FileNotFoundException

/**
 * Handles reads and writes to file
 */
object FileHandler {

    private const val fileName = "Credentials.json"

    private fun saveFile(context: Context, fileName: String = this.fileName, text: String){
        File(context.filesDir,fileName).writeText(text)
    }

    private fun listToJson(list: MutableList<Credential>, pretty: Boolean = false): String{
        if (pretty){
            return GsonBuilder().setPrettyPrinting().create().toJson(list)
        }
        return Gson().toJson(list)
    }

    /**
     * Reads file contents to a String
     */
    private fun readFile(context: Context, fileName: String): String {
        return File(context.filesDir,fileName).readText()
    }

    /**
     * Reads and retrieves a credential list
     */
    fun readCredentialList(context: Context, fileName: String) : MutableList<Credential> {
        val fileContents = try {
            readFile(context, fileName)
        } catch (exception: FileNotFoundException) {
            //Return empty array if not found
                Logger.d("Credential list", exception.message!!)
            return mutableListOf()
        }
        return Gson().fromJson(fileContents,
            object : TypeToken<MutableList<Credential>>() {}.type)
    }

    fun saveCredentialList(context: Context, fileName: String, list: MutableList<Credential>) {
        val text = listToJson(list, true)
        saveFile(context,fileName, text)
    }
}