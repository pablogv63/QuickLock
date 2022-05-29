package com.pablogv63.quicklock.domain.use_case

import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import com.pablogv63.quicklock.domain.util.CredentialOrder
import com.pablogv63.quicklock.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCredentialsWithCategories(
    private val repository: CredentialRepository
) {

    operator fun invoke(
        credentialOrder: CredentialOrder = CredentialOrder.Name(OrderType.Descending)
    ): Flow<List<CredentialWithCategoryList>> {
        return repository.getCredentialsWithCategories().map { credentials ->
            when(credentialOrder.orderType){
                is OrderType.Ascending -> {
                    when(credentialOrder) {
                        is CredentialOrder.Name -> credentials.sortedBy { it.credential.name.lowercase() }
                        is CredentialOrder.Username -> credentials.sortedBy { it.credential.username.lowercase() }
                        is CredentialOrder.ExpirationDate -> credentials.sortedBy { it.credential.expirationDate }
                        is CredentialOrder.LastAccessed -> credentials.sortedBy { it.credential.lastAccess }
                        is CredentialOrder.LastModified -> credentials.sortedBy { it.credential.lastModified }
                    }
                }
                is OrderType.Descending -> {
                    when(credentialOrder) {
                        is CredentialOrder.Name -> credentials.sortedByDescending { it.credential.name.lowercase() }
                        is CredentialOrder.Username -> credentials.sortedByDescending { it.credential.username.lowercase() }
                        is CredentialOrder.ExpirationDate -> credentials.sortedByDescending { it.credential.expirationDate }
                        is CredentialOrder.LastAccessed -> credentials.sortedByDescending { it.credential.lastAccess }
                        is CredentialOrder.LastModified -> credentials.sortedByDescending { it.credential.lastModified }
                    }
                }
            }
        }
    }
}