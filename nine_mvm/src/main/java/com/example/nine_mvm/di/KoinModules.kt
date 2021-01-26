package com.example.nine_mvm.di

import com.example.nine_mvm.domain.ForecastDataConverter
import com.example.nine_mvm.domain.ForecastDataConverterImpl
import com.example.nine_mvm.model.CityRepo
import com.example.nine_mvm.model.CityRepoImpl
import com.example.nine_mvm.model.db.CityDB
import com.example.nine_mvm.model.forecast_service.ForecastService
import com.example.nine_mvm.model.forecast_service.ForecastServiceImpl
import com.example.nine_mvm.view.city_chooser.CityViewModel
import com.example.nine_mvm.view.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modelModule = module {
    single<ForecastService> { ForecastServiceImpl() }
    single { CityDB.getDB(get()) }
    single<CityRepo> { CityRepoImpl(get()) }
}

val domainModule = module {
    single<ForecastDataConverter> {
        ForecastDataConverterImpl(
            forecastService = get(),
            appContext = get()
        )
    }
}

val viewModelModule = module {
    viewModel { MainViewModel(forecastDataConverter = get()) }
    viewModel { CityViewModel(cityRepo = get()) }
}
