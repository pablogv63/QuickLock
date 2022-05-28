package com.pablogv63.quicklock.domain.use_case

data class CredentialUseCases(
    val getCredentialsWithCategories: GetCredentialsWithCategories,
    val getCredentialWithCategoriesFromId: GetCredentialWithCategoriesFromId,
    val getCategories: GetCategories,
    val addCredential: AddCredential,
    val addCredentialWithCategories: AddCredentialWithCategories,
    val editCredentialWithCategories: EditCredentialWithCategories,
    val deleteCredential: DeleteCredential,
    val addCategory: AddCategory,
    val repopulateDatabase: RepopulateDatabase
)