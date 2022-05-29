package com.pablogv63.quicklock.domain.util

sealed class CredentialFilter {
    object Name: CredentialFilter()
    object Username: CredentialFilter()
    object Category: CredentialFilter()
}