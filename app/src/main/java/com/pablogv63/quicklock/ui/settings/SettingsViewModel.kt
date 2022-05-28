package com.pablogv63.quicklock.ui.settings

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.pablogv63.quicklock.data.FileUtils.loadFromFile
import com.pablogv63.quicklock.data.FileUtils.saveCSVToFile
import com.pablogv63.quicklock.data.FileUtils.saveToFile
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import com.pablogv63.quicklock.domain.util.DateParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class SettingsViewModel(
    private val credentialUseCases: CredentialUseCases
): ViewModel() {

    fun onEvent(event: SettingsEvent) {
        when (event){
            is SettingsEvent.ExportDatabase -> {
                viewModelScope.launch(Dispatchers.IO){
                    exportDatabaseToJSONFile(uri = event.uri, context = event.context)
                }
            }
            is SettingsEvent.ImportDatabase -> {
                viewModelScope.launch(Dispatchers.IO){
                    importDatabaseFromJsonFile(uri = event.uri, context = event.context)
                }
            }
        }
    }

    private suspend fun exportDatabaseToJSONFile(
        uri: Uri,
        context: Context
    ){
        credentialUseCases.getCredentialsWithCategories().collectLatest {
            val gson = GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate::class.java, DateParser.LocalDateAdapter())
                .create()
            val json = gson.toJson(it)
            saveToFile(uri = uri, contentResolver = context.contentResolver, content = json)
        }
    }

    private suspend fun importDatabaseFromJsonFile(
        uri: Uri,
        context: Context
    ){
        viewModelScope.launch {
            val gson = GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate::class.java, DateParser.LocalDateAdapter())
                .create()
            loadFromFile(uri = uri, contentResolver = context.contentResolver)?.let {
                val credentialsWithCategories = gson.fromJson(
                    it, Array<CredentialWithCategoryList>::class.java
                ).toList()
                credentialUseCases.repopulateDatabase(credentialsWithCategories)
            }
        }

    }

}