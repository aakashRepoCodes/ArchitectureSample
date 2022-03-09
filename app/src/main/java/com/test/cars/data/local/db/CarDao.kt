package com.test.cars.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.cars.domain.model.Manufacturer
import io.reactivex.Single

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertManufacturer(list: List<Manufacturer>): List<Long>

    @Query("SELECT * FROM cars WHERE name LIKE :search || '%'")
    fun searchCars(search: String): Single<List<Manufacturer>>

}