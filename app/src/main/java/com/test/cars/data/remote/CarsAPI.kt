package com.test.cars.data.remote

import com.test.cars.data.common.Constants
import com.test.cars.data.model.ManufacturerDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsAPI {

    @GET("v1/car-types/manufacturer")
    suspend fun getManufacturersList(
        @Query("wa_key") key: String = Constants.API_KEY,
        @Query("page") page: Int,
        @Query("pageSize") size: Int
    ): ManufacturerDTO

    @GET("v1/car-types/main-types")
    fun getModelList(
        @Query("wa_key") key: String = Constants.API_KEY,
        @Query("manufacturer") brand: String,
    ): Single<ManufacturerDTO>

    @GET("v1/car-types/built-dates")
    fun getModelListByYear(
        @Query("wa_key") key: String = Constants.API_KEY,
        @Query("manufacturer") brand: String,
        @Query("main-type") model: String,
    ): Single<ManufacturerDTO>

}