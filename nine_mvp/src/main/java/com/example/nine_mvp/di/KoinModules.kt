package com.example.nine_mvp.di

import com.example.nine_mvp.domain.ForecastDataConverter
import com.example.nine_mvp.domain.ForecastDataConverterImpl
import com.example.nine_mvp.model.CityRepo
import com.example.nine_mvp.model.CityRepoImpl
import com.example.nine_mvp.model.db.CityDB
import com.example.nine_mvp.model.forecast_service.ForecastService
import com.example.nine_mvp.model.forecast_service.ForecastServiceImpl
import com.example.nine_mvp.view.city_chooser.CityPresenter
import com.example.nine_mvp.view.city_chooser.CityPresenterImpl
import com.example.nine_mvp.view.main.MainPresenter
import com.example.nine_mvp.view.main.MainPresenterImpl
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

val presenterModule = module {
    single< MainPresenter> { MainPresenterImpl(forecastDataConverter = get()) }
    single<CityPresenter> { CityPresenterImpl(cityRepo = get()) }
}
