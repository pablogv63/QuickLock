package com.pablogv63.quicklock.credential.exceptions

/* Credential */
class DuplicateCredentialException(message: String) : Exception(message)

class NotFoundCredentialException(message: String) : Exception(message)

/* Field */
class DuplicateFieldException(message: String): Exception(message)

class NotFoundFieldException(message: String) : Exception(message)