package com.test.cars.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.test.cars.data.local.db.CarDatabase
import com.test.cars.data.model.ManufacturerDTO
import com.test.cars.domain.model.Manufacturer
import com.test.cars.data.remote.CarsAPI
import com.test.cars.domain.repository.AutoMobileRepository
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

class AutoMobileRepositoryImpl(
    private val carsAPI: CarsAPI,
    private val database: CarDatabase,
    private val pager: Pager<Int, Pair<String, String>>
) : AutoMobileRepository {

    override fun getManufacturerList(): Flow<PagingData<Pair<String, String>>> {
        return pager.flow
    }

    override fun searchCarByManufacturer(query: String): Single<List<Manufacturer>> {
        return database.getArticleDao().searchCars(search = query)
    }

    override fun getManufacturerModelsList(brandId: String): Single<ManufacturerDTO> {
        return carsAPI.getModelList( brand = brandId )
    }

    override fun searchCarByModelOrYear(brandId: String, modelId: String): Single<ManufacturerDTO> {
      return carsAPI.getModelListByYear(brand = brandId, model = modelId)
    }

}