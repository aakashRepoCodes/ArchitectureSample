package com.test.cars.domain.model


data class ManufacturerModelYear(

    val modelId: String,

    val list:  Map<String, String>
){
    companion object {
        fun toList(map: Map<String, String>): MutableList<ManufacturerModel> {
            val carsList = mutableListOf<ManufacturerModel>()
            for ((key, value) in map) {
                carsList.add(ManufacturerModel(id = key, name = value))
            }
            return carsList
        }
    }
}