package com.pablogv63.quicklock.ui.credentials

import com.pablogv63.quicklock.domain.util.CredentialOrder

sealed class CredentialsEvent {
    data class Order(val credentialOrder: CredentialOrder): CredentialsEvent()
    object ToggleOrderSection: CredentialsEvent()
}