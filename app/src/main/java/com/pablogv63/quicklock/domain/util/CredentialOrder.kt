package com.pablogv63.quicklock.domain.util

sealed class CredentialOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): CredentialOrder(orderType)
    class Username(orderType: OrderType): CredentialOrder(orderType)
    class ExpirationDate(orderType: OrderType): CredentialOrder(orderType)
    class LastAccessed(orderType: OrderType): CredentialOrder(orderType)
    class LastModified(orderType: OrderType): CredentialOrder(orderType)

    fun copy(orderType: OrderType): CredentialOrder {
        return when(this){
            is Name -> Name(orderType)
            is Username -> Username(orderType)
            is ExpirationDate -> ExpirationDate(orderType)
            is LastAccessed -> LastAccessed(orderType)
            is LastModified -> LastModified(orderType)
        }
    }
}