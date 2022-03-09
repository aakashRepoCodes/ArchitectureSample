package com.test.cars.domain.usecase

import com.test.cars.domain.model.Manufacturer
import com.test.cars.domain.repository.AutoMobileRepository
import io.reactivex.Single

class GetManufacturerSearchUseCase(private val repository: AutoMobileRepository) {

    operator fun invoke(query: String): Single<List<Manufacturer>> {
        return repository.searchCarByManufacturer(query)
    }
}
