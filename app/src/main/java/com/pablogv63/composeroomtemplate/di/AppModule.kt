package com.pablogv63.composeroomtemplate.di

import androidx.room.Room
import com.pablogv63.composeroomtemplate.application.ComposeRoomTemplateApp
import com.pablogv63.composeroomtemplate.data.data_source.AppDatabase
import com.pablogv63.composeroomtemplate.data.repository.ExamplePersonRepositoryImpl
import com.pablogv63.composeroomtemplate.domain.repository.ExamplePersonRepository
import com.pablogv63.composeroomtemplate.domain.use_case.AddExamplePerson
import com.pablogv63.composeroomtemplate.domain.use_case.ExamplePersonUseCases
import com.pablogv63.composeroomtemplate.domain.use_case.GetExamplePeople
import com.pablogv63.composeroomtemplate.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ComposeRoomTemplateApp> {
        ComposeRoomTemplateApp()
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
    single<ExamplePersonRepository> {
        ExamplePersonRepositoryImpl(get<AppDatabase>().examplePersonDao())
    }
    single<ExamplePersonUseCases> {
        ExamplePersonUseCases(
            GetExamplePeople(get()),
            AddExamplePerson(get())
        )
    }
    viewModel {
        MainViewModel(get())
    }
}