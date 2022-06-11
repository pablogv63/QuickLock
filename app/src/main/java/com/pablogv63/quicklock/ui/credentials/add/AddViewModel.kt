package com.pablogv63.quicklock.ui.credentials.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablogv63.quicklock.domain.model.Category
import com.pablogv63.quicklock.domain.model.Credential
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import com.pablogv63.quicklock.domain.use_case.form.FormUseCases
import com.pablogv63.quicklock.domain.util.DateParser.toLocalDate
import com.pablogv63.quicklock.ui.credentials.form.FormEvent
import com.pablogv63.quicklock.ui.credentials.form.FormState
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

    var formState by mutableStateOf(FormState())
    var addState by mutableStateOf(AddState())

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
                formState = formState.copy(
                    categories = categories
                )
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(formEvent: FormEvent){
        when (formEvent) {
            is FormEvent.NameChanged -> {
                val nameResult = formUseCases.validateName(formEvent.name)
                formState = formState.copy(
                    name = formEvent.name,
                    nameError = nameResult.errorMessage
                )
            }
            is FormEvent.UsernameChanged -> {
                val usernameResult = formUseCases.validateUsername(formEvent.username)
                formState = formState.copy(
                    username = formEvent.username,
                    usernameError = usernameResult.errorMessage
                )
            }
            is FormEvent.PasswordChanged -> {
                val passwordResult = formUseCases.validatePassword(formEvent.password)
                formState = formState.copy(
                    password = formEvent.password,
                    passwordError = passwordResult.errorMessage
                )
            }
            is FormEvent.RepeatedPasswordChanged -> {
                val repeatedPasswordResult = formUseCases.validateRepeatedPassword(
                    password = formState.password, repeatedPassword = formEvent.repeatedPassword
                )
                formState = formState.copy(
                    repeatedPassword = formEvent.repeatedPassword,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage
                )
            }
            is FormEvent.ExpirationDateChanged -> {
                val expirationDateResult = formUseCases.validateExpirationDate(
                    expirationDate = formEvent.expirationDate
                )
                formState = formState.copy(
                    expirationDate = formEvent.expirationDate,
                    expirationDateError = expirationDateResult.errorMessage
                )
            }
            is FormEvent.CategorySelected -> {
                val currentCategories = formState.selectedCategories.toMutableList()
                currentCategories.add(formEvent.category)
                formState = formState.copy(
                    selectedCategories = currentCategories.toList()
                )
            }
            is FormEvent.CategoryRemoved -> {
                val currentCategories = formState.selectedCategories.toMutableList()
                currentCategories.remove(formEvent.category)
                formState = formState.copy(
                    selectedCategories = currentCategories.toList()
                )
            }
            is FormEvent.NewCategory -> {
                // Add category to list
                val currentCategories = formState.categories.toMutableList()
                val selectedCategories = formState.selectedCategories.toMutableList()
                val newCategory = Category(name = formEvent.name, colour = formEvent.color.toArgb())
                currentCategories.add(newCategory)
                selectedCategories.add(newCategory)
                formState = formState.copy(
                    categories = currentCategories,
                    selectedCategories = selectedCategories
                )
            }
            is FormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData(){
        val nameResult = formUseCases.validateName(formState.name)
        val usernameResult = formUseCases.validateUsername(formState.username)
        val passwordResult = formUseCases.validatePassword(formState.password)
        val repeatedPasswordResult = formUseCases.validateRepeatedPassword(
            formState.password, formState.repeatedPassword
        )
        val expirationDateResult = formUseCases.validateExpirationDate(formState.expirationDate)

        formState = formState.copy(
            nameError = nameResult.errorMessage,
            usernameError = usernameResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            repeatedPasswordError = repeatedPasswordResult.errorMessage,
            expirationDateError = expirationDateResult.errorMessage,
        )

        val hasError = listOf(
            nameResult,
            usernameResult,
            passwordResult,
            repeatedPasswordResult,
            expirationDateResult
        ).any { !it.successful }
        if (hasError) { return }

        viewModelScope.launch {
            val credentialWithCategoryList = stateToCredentialWithCategoryList()
            credentialUseCases.addCredentialWithCategories(credentialWithCategoryList)
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    private fun stateToCredentialWithCategoryList(): CredentialWithCategoryList {
        return CredentialWithCategoryList(
            Credential(
                credentialId = 0,
                name = formState.name,
                username = formState.username,
                password = formState.password,
                expirationDate = formState.expirationDate.toLocalDate(),
                lastModified = LocalDate.now(),
                lastAccess = LocalDate.now()
            ),
            categories = formState.selectedCategories
        )
    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }
}

