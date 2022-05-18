package com.pablogv63.quicklock.domain.util

sealed class CredentialOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): CredentialOrder(orderType)

    fun copy(orderType: OrderType): CredentialOrder {
        return when(this){
            is Name -> Name(orderType)
        }
    }
}