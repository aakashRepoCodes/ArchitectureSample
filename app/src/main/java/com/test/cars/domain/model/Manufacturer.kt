package com.test.cars.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Manufacturer(
    @PrimaryKey
    var id: String,
    val name:  String
)
