package com.test.cars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.cars.domain.usecase.GetManufacturersUseCase
import kotlinx.coroutines.flow.Flow

class ManufacturerHomeViewModel(
    private val getManufacturersUseCase: GetManufacturersUseCase
) : ViewModel() {

    lateinit var allCarListDataState: Flow<PagingData<Pair<String, String>>>

    init {
        getAllCars()
    }

    private fun getAllCars() {
        allCarListDataState = getManufacturersUseCase.invoke().cachedIn(viewModelScope)
    }

}