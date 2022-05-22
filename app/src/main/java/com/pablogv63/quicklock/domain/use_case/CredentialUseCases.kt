package com.pablogv63.quicklock.domain.use_case

data class CredentialUseCases(
    val getCredentialsWithCategories: GetCredentialsWithCategories,
    val getCategories: GetCategories,
    val addCredential: AddCredential,
    val addCredentialWithCategories: AddCredentialWithCategories,
    val addCategory: AddCategory
)