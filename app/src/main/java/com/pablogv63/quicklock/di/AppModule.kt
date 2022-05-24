package com.pablogv63.quicklock.di

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.room.Room
import com.pablogv63.quicklock.application.QuickLockApp
import com.pablogv63.quicklock.data.data_source.AppDatabase
import com.pablogv63.quicklock.data.repository.CategoryRepositoryImpl
import com.pablogv63.quicklock.data.repository.CredentialCategoryPairRepositoryImpl
import com.pablogv63.quicklock.data.repository.CredentialRepositoryImpl
import com.pablogv63.quicklock.domain.model.CredentialWithCategoryList
import com.pablogv63.quicklock.domain.repository.CategoryRepository
import com.pablogv63.quicklock.domain.repository.CredentialCategoryPairRepository
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import com.pablogv63.quicklock.domain.use_case.*
import com.pablogv63.quicklock.domain.use_case.form.*
import com.pablogv63.quicklock.ui.MainViewModel
import com.pablogv63.quicklock.ui.credentials.add.AddViewModel
import com.pablogv63.quicklock.ui.credentials.detail.DetailViewModel
import com.pablogv63.quicklock.ui.credentials.edit.EditViewModel
import com.pablogv63.quicklock.ui.credentials.list.CredentialsViewModel
import com.pablogv63.quicklock.ui.navigation.NavigationScreen
import com.pablogv63.quicklock.ui.navigation.NavigationScreens
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<QuickLockApp> {
        QuickLockApp()
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
    single<CredentialRepository> {
        CredentialRepositoryImpl(get<AppDatabase>().credentialDao())
    }
    single<CategoryRepository> {
        CategoryRepositoryImpl(get<AppDatabase>().categoryDao())
    }
    single<CredentialCategoryPairRepository> {
        CredentialCategoryPairRepositoryImpl(get<AppDatabase>().credentialWithCategoryDao())
    }
    single<CredentialUseCases> {
        CredentialUseCases(
            GetCredentialsWithCategories(get()),
            GetCredentialWithCategoriesFromId(get()),
            GetCategories(get()),
            AddCredential(get()),
            AddCredentialWithCategories(get(),get(),get()),
            EditCredentialWithCategories(get(),get(),get()),
            DeleteCredential(get(),get()),
            AddCategory(get())
        )
    }
    single<FormUseCases> {
        FormUseCases(
            ValidateName(),
            ValidateUsername(),
            ValidatePassword(),
            ValidateRepeatedPassword(),
            ValidateExpirationDate(),
            ValidateCategory()
        )
    }
    viewModel<MainViewModel> {
        MainViewModel(get(), get())
    }
    viewModel<CredentialsViewModel> {
        CredentialsViewModel(get())
    }
    viewModel<AddViewModel> {
        AddViewModel(get(),get())
    }
    viewModel<EditViewModel> { (credentialId: Int) ->
        EditViewModel(get(),get(),credentialId)
    }
    viewModel<DetailViewModel> { (credentialId: Int) ->
        DetailViewModel(get(),credentialId)
    }
}