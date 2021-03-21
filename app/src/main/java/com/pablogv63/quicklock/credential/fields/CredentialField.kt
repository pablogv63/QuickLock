package com.pablogv63.quicklock.credential.fields

data class CredentialField(val id: CredentialFields.FieldType, var value: String) {

    override fun equals(other: Any?): Boolean {
        if (other is CredentialFields.FieldType) {
            return id == other
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return id.name + " : " + value
    }
}