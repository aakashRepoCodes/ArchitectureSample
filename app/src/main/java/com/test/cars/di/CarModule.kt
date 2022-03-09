package com.test.cars.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.test.cars.data.common.Constants.BASE_URL
import com.test.cars.data.common.CoroutineContextProvider
import com.test.cars.data.local.db.CarDatabase
import com.test.cars.data.paging.CarPagingSource
import com.test.cars.data.remote.CarsAPI
import com.test.cars.data.repository.AutoMobileRepositoryImpl
import com.test.cars.domain.repository.AutoMobileRepository
import com.test.cars.domain.usecase.GetManufacturersUseCase
import com.test.cars.domain.usecase.GetManufacturerModelUseCase
import com.test.cars.domain.usecase.GetManufacturerSearchUseCase
import com.test.cars.domain.usecase.GetManufacturerModelSearchUseCase
import com.test.cars.presentation.viewmodel.ManufacturerModelDetailViewModel
import com.test.cars.presentation.viewmodel.ManufacturerHomeViewModel
import com.test.cars.presentation.viewmodel.ManufacturerSearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val carsModule = module {

    factory<CoroutineContextProvider> { object : CoroutineContextProvider {} }

    single<CarsAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarsAPI::class.java)
    }

    factory<AutoMobileRepository> {
        AutoMobileRepositoryImpl(database = get(), pager = get(), carsAPI = get())
    }

    viewModel {
        ManufacturerHomeViewModel(
            getManufacturersUseCase = get()
        )
    }

    viewModel {
        ManufacturerSearchViewModel(
            getManufacturerSearchUseCase = get(),
            coroutineContextProvider = get()
        )
    }

    viewModel {
        ManufacturerModelDetailViewModel(
            getManufacturerModelUseCse = get(),
            coroutineContextProvider = get()
        )
    }

    factory {
        GetManufacturersUseCase(repository = get())
    }

    factory {
        GetManufacturerModelSearchUseCase(repository = get())
    }

    factory {
        GetManufacturerSearchUseCase(repository = get())
    }

    factory {
        GetManufacturerModelUseCase(repository = get())
    }

    single {
        CarDatabase(context = androidContext())
    }

    factory {
        Pager(config = PagingConfig(pageSize = 15, prefetchDistance = 5, enablePlaceholders = true),
            pagingSourceFactory = {
                CarPagingSource(carsAPI = get(), database = get())
            })
    }

}