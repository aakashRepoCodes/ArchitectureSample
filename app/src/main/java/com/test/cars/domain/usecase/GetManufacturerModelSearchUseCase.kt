package com.test.cars.domain.usecase

import com.test.cars.data.model.ManufacturerDTO
import com.test.cars.domain.repository.AutoMobileRepository
import io.reactivex.Single

class GetManufacturerModelSearchUseCase(private val repository: AutoMobileRepository) {

    operator fun invoke(brandId: String, modelId: String =""): Single<ManufacturerDTO> {
        return repository.searchCarByModelOrYear(brandId = brandId, modelId=modelId)
    }
}
