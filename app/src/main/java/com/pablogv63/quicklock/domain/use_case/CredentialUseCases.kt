package com.pablogv63.quicklock.domain.use_case

data class CredentialUseCases(
    val getCredentialsWithCategories: GetCredentialsWithCategories,
    val addCredential: AddCredential
)