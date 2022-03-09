package com.test.cars.domain.usecase

import androidx.paging.PagingData
import com.test.cars.domain.repository.AutoMobileRepository
import kotlinx.coroutines.flow.Flow

class GetManufacturersUseCase(private val repository: AutoMobileRepository) {

    operator fun invoke(): Flow<PagingData<Pair<String, String>>> {
        return repository.getManufacturerList()
    }
}