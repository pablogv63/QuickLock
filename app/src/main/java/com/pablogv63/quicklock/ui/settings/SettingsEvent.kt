package com.pablogv63.quicklock.ui.settings

import android.content.Context
import android.net.Uri

sealed class SettingsEvent {
    data class ExportDatabase(val context: Context, val uri: Uri): SettingsEvent()
    data class ImportDatabase(val context: Context, val uri: Uri): SettingsEvent()
}
