package com.pablogv63.quicklock.credential.fields

import com.pablogv63.quicklock.credential.exceptions.DuplicateFieldException
import com.pablogv63.quicklock.credential.exceptions.NotFoundFieldException

/**
 * Clase que representa a los campos de una credencial
 * (Fields es un diccionario que guarda valores (FieldType id, String value))
 */
class CredentialFields(private var fields: MutableList<CredentialField> = mutableListOf()) {

    //Get class name (Unused)
    private val logTag = this::class.simpleName!!

    //Error messages
    private val duplicateErrorMessage = "Duplicate field found: "
    private val notFoundErrorMessage = "Field not found: "

    //Field types
    enum class FieldType {
        PASSWORD, USERNAME, EMAIL
    }


    fun add(field: CredentialField) {
        //Duplicate field: attempting to add field multiple times
        if (field in fields) {
            throw DuplicateFieldException(duplicateErrorMessage + field.id.name)
        }
        fields.add(field)
    }

    fun update(field: CredentialField) {
        if (field !in fields){
            throw NotFoundFieldException(notFoundErrorMessage + field.id.name)
        }
        // Abajo extendemos la funcionalidad de MutableList para poder poner esta línea
        fields[field] = field

        //Sino sería así:
        //fields[fields.indexOf(field)] = field
    }

    fun delete(field: CredentialField) {
        if (field !in fields){
            throw NotFoundFieldException(notFoundErrorMessage + field.id.name)
        }
        fields.remove(field)
    }

    fun getCredentialFields(): MutableList<CredentialField> {
        return fields
    }

    fun getFieldName(id: FieldType): String {
        return id.name
    }

    /**
     * Extension of MutableList<CredentialField>
     *     Using a field as "index", replaces field with newField
     */
    private operator fun <CredentialField> MutableList<CredentialField>.set(field: CredentialField, newField: CredentialField): CredentialField {
        val index = this.indexOf(field)
        this[index] = newField
        return field
    }

    override fun toString(): String {
        var s = "["
        for (field in fields) {
            s += "$field, "
        }
        s = if (s.length > 1) s.dropLast(2) else ""
        return "$s]"
    }
}
