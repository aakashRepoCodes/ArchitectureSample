package com.test.cars.domain.usecase

import com.test.cars.data.model.ManufacturerDTO
import com.test.cars.domain.repository.AutoMobileRepository
import io.reactivex.Single

class GetManufacturerModelUseCase(private val repository: AutoMobileRepository) {

    operator fun invoke(brandId: String): Single<ManufacturerDTO> {
      return repository.getManufacturerModelsList(brandId)
    }
}