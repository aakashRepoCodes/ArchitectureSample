package com.test.cars.domain.repository

import androidx.paging.PagingData
import com.test.cars.data.model.ManufacturerDTO
import com.test.cars.domain.model.Manufacturer
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface AutoMobileRepository {

    fun getManufacturerList(): Flow<PagingData<Pair<String, String>>>

    fun searchCarByManufacturer(query: String): Single<List<Manufacturer>>

    fun getManufacturerModelsList(brandId: String): Single<ManufacturerDTO>

    fun searchCarByModelOrYear(brandId: String, modelId: String): Single<ManufacturerDTO>
}