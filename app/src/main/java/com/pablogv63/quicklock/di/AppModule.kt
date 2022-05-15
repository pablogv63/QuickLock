package com.pablogv63.quicklock.di

import androidx.room.Room
import com.pablogv63.quicklock.application.QuickLockApp
import com.pablogv63.quicklock.data.data_source.AppDatabase
import com.pablogv63.quicklock.data.repository.CredentialRepositoryImpl
import com.pablogv63.quicklock.domain.repository.CredentialRepository
import com.pablogv63.quicklock.domain.use_case.AddCredential
import com.pablogv63.quicklock.domain.use_case.CredentialUseCases
import com.pablogv63.quicklock.domain.use_case.GetCredentials
import com.pablogv63.quicklock.ui.MainViewModel
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
        ).build()
    }
    single<CredentialRepository> {
        CredentialRepositoryImpl(get<AppDatabase>().credentialDao())
    }
    single<CredentialUseCases> {
        CredentialUseCases(
            GetCredentials(get()),
            AddCredential(get())
        )
    }
    viewModel {
        MainViewModel(get())
    }
}