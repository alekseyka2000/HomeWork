package com.example.eleven.di

import com.example.eleven.model.ContactRepository
import com.example.eleven.model.ContactRepositoryImpl
import com.example.eleven.model.db.ContactDB
import com.example.eleven.view.add.AddContactViewModel
import com.example.eleven.view.edit.EditContactViewModel
import com.example.eleven.view.list.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val serviceModule = module {
    single { ContactDB.getDB(context = get()).contactDAO() }
    single<ContactRepository> { ContactRepositoryImpl(dataBase = get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(repository = get(), contextApp = get()) }
    viewModel { AddContactViewModel(repository = get(), contextApp = get()) }
    viewModel { EditContactViewModel(repository = get(), contextApp = get()) }
}
