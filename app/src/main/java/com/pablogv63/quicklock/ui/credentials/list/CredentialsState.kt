package com.pablogv63.quicklock.ui.credentials.list

import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.util.CredentialOrder
import com.pablogv63.quicklock.domain.util.OrderType

data class CredentialsState (
    val credentialsWithCategories: List<CredentialWithCategoryList> = emptyList(),
    val credentialOrder: CredentialOrder = CredentialOrder.LastAccessed(OrderType.Descending),
    val isOrderMenuVisible: Boolean = false,
    val isSearchBarVisible: Boolean = false,
    val searchBarText: String = "",
    val matchedCredentialsWithCategories: List<CredentialWithCategoryList> = emptyList(),
    val filterByName: Boolean = true,
    val filterByUsername: Boolean = true,
    val filterByCategory: Boolean = true
)