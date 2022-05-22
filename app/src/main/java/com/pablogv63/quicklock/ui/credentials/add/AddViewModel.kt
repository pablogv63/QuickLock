package com.pablogv63.quicklock.ui.credentials.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import com.pablogv63.quicklock.domain.use_case.form.FormUseCases
import com.pablogv63.quicklock.domain.util.DateParser.toLocalDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddViewModel(
    private val credentialUseCases: CredentialUseCases,
    private val formUseCases: FormUseCases
): ViewModel() {

    var state by mutableStateOf(AddFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private var getCategoriesJob: Job? = null

    init {
        getCategories()
    }

    private fun getCategories(){
        getCategoriesJob?.cancel()
        getCategoriesJob = credentialUseCases.getCategories()
            .onEach { categories ->
                state = state.copy(
                    categories = categories
                )
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(addFormEvent: AddFormEvent){
        when (addFormEvent) {
            is AddFormEvent.NameChanged -> {
                val nameResult = formUseCases.validateName(addFormEvent.name)
                state = state.copy(
                    name = addFormEvent.name,
                    nameError = nameResult.errorMessage
                )
            }
            is AddFormEvent.UsernameChanged -> {
                val usernameResult = formUseCases.validateUsername(addFormEvent.username)
                state = state.copy(
                    username = addFormEvent.username,
                    usernameError = usernameResult.errorMessage
                )
            }
            is AddFormEvent.PasswordChanged -> {
                val passwordResult = formUseCases.validatePassword(addFormEvent.password)
                state = state.copy(
                    password = addFormEvent.password,
                    passwordError = passwordResult.errorMessage
                )
            }
            is AddFormEvent.RepeatedPasswordChanged -> {
                val repeatedPasswordResult = formUseCases.validateRepeatedPassword(
                    password = state.password, repeatedPassword = addFormEvent.repeatedPassword
                )
                state = state.copy(
                    repeatedPassword = addFormEvent.repeatedPassword,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage
                )
            }
            is AddFormEvent.ExpirationDateChanged -> {
                val expirationDateResult = formUseCases.validateExpirationDate(
                    expirationDate = addFormEvent.expirationDate
                )
                state = state.copy(
                    expirationDate = addFormEvent.expirationDate,
                    expirationDateError = expirationDateResult.errorMessage
                )
            }
            is AddFormEvent.CategoryChanged -> {
                val categoryResult = formUseCases.validateCategory.invoke(
                    categoryName = addFormEvent.category,
                    list = state.categories
                )
                state = state.copy(
                    category = addFormEvent.category,
                    categoryError = categoryResult.errorMessage
                )
            }
            is AddFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData(){
        val nameResult = formUseCases.validateName(state.name)
        val usernameResult = formUseCases.validateUsername(state.username)
        val passwordResult = formUseCases.validatePassword(state.password)
        val repeatedPasswordResult = formUseCases.validateRepeatedPassword(
            state.password, state.repeatedPassword
        )
        val expirationDateResult = formUseCases.validateExpirationDate(state.expirationDate)
        val categoryResult = formUseCases.validateCategory(state.category, state.categories)

        state = state.copy(
            nameError = nameResult.errorMessage,
            usernameError = usernameResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
            expirationDateError = expirationDateResult.errorMessage,
            categoryError = categoryResult.errorMessage
        )

        val hasError = listOf(
            nameResult,
            usernameResult,
            passwordResult,
            repeatedPasswordResult,
            expirationDateResult,
            categoryResult
        ).any { !it.successful }
        if (hasError) { return }

        viewModelScope.launch {
            val credentialWithCategoryList = stateToCredentialWithCategoryList()
            credentialUseCases.addCredentialWithCategories(credentialWithCategoryList)
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    private fun stateToCredentialWithCategoryList(): CredentialWithCategoryList {
        val categories =
            if (state.category.isBlank())
                listOf()
            else
                listOf(state.categories.first { state.category == it.name })
        return CredentialWithCategoryList(
            Credential(
                credentialId = 0,
                name = state.name,
                username = state.username,
                password = state.password,
                expirationDate = state.expirationDate.toLocalDate(),
                lastModified = LocalDate.now(),
                lastAccess = LocalDate.now()
            ),
            categories = categories
        )
    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }
}

