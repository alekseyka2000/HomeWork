package com.example.eight.di

import com.example.eight.CustomThreadHandler
import com.example.eight.model.ContactRepository
import com.example.eight.model.ContactRepositoryImpl
import com.example.eight.model.db.ContactDB
import com.example.eight.view.add.AddContactViewModel
import com.example.eight.view.edit.EditContactViewModel
import com.example.eight.view.list.MainViewModel
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
