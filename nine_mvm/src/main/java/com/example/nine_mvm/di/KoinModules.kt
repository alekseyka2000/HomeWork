package com.example.nine_mvm.di

import com.example.nine_mvm.domain.ForecastDataConverter
import com.example.nine_mvm.domain.ForecastDataConverterImpl
import com.example.nine_mvm.model.forecast_service.ForecastService
import com.example.nine_mvm.model.forecast_service.ForecastServiceImpl
import org.koin.dsl.module

val modelModule = module {
    single<ForecastService> { ForecastServiceImpl() }
}

val domainModule = module {
    single <ForecastDataConverter> { ForecastDataConverterImpl(forecastService = get()) }
}
