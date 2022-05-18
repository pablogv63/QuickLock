package com.pablogv63.quicklock.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}