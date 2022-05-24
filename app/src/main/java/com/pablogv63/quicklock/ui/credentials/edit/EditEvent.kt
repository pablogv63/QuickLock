package com.pablogv63.quicklock.ui.credentials.edit

sealed class EditEvent {
    data class Delete(val id: Int): EditEvent()
}
