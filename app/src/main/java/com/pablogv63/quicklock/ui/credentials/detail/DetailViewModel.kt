package com.pablogv63.quicklock.ui.credentials.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import com.pablogv63.quicklock.domain.util.DateParser.toParsedDayMonthYearString
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailViewModel(
    private val credentialUseCases: CredentialUseCases,
    private val credentialId: Int
): ViewModel() {

    var detailState by mutableStateOf(DetailState())

    private var getCredentialJob: Job? = null

    init {
        initializeFields()
    }

    private fun initializeFields(){
        getCredentialJob?.cancel()
        getCredentialJob = credentialUseCases.getCredentialWithCategoriesFromId(credentialId)
            .onEach { credentialWithCategoryList ->
                val credential = credentialWithCategoryList.credential
                detailState = detailState.copy(
                    credentialId = credentialId,
                    name = credential.name,
                    username = credential.username,
                    password = credential.password,
                    expirationDate = credential.expirationDate?.toParsedDayMonthYearString() ?: "",
                    categories = credentialWithCategoryList.categories,
                    lastModified = credential.lastModified.toParsedDayMonthYearString(),
                    lastAccess = credential.lastAccess.toParsedDayMonthYearString()
                )
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(detailEvent: DetailEvent) {
        when (detailEvent) {
            is DetailEvent.Edit -> {
                // Call edit
            }
        }
    }
}