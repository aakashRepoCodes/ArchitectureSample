package com.test.cars.data.model

import com.google.gson.annotations.SerializedName
import com.test.cars.domain.model.Manufacturer

data class ManufacturerDTO(

    val page: Int,

    val pagSize: Int,

    val totalPageCount: Int,

    @SerializedName("wkda")
    val list:  Map<String, String>
) {
    companion object {
        fun ManufacturerDTO.toList(): MutableList<Manufacturer> {
            val map = list
            val carsList = mutableListOf<Manufacturer>()
            for ((key, value) in map) {
                carsList.add(Manufacturer(id = key, name = value))
            }
            return carsList
        }
    }
}
