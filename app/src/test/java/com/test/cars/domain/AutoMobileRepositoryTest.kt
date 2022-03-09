
package com.test.cars.domain

import androidx.paging.Pager
import com.test.cars.data.local.db.CarDatabase
import com.test.cars.data.remote.CarsAPI
import com.test.cars.domain.model.Manufacturer
import com.test.cars.data.repository.AutoMobileRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AutoMobileRepositoryTest {

    @MockK
    private lateinit var database: CarDatabase

    @MockK
    private lateinit var carsAPI: CarsAPI

    @RelaxedMockK
    private lateinit var pager: Pager<Int, Pair<String, String>>

    private lateinit var sut: AutoMobileRepositoryImpl

    @Before
    fun init() {
        MockKAnnotations.init(this)
        sut = AutoMobileRepositoryImpl(carsAPI, database, pager)
    }

    @Test
    fun `Given user searches with a query, when query is checked with db values , then matching results are returned`() =
        runBlocking {
            //Given
            val query = "audi"
            val list = mutableListOf<Manufacturer>().apply {
                add(Manufacturer("1", "Bmw"))
                add(Manufacturer("2", "Bugatti"))
            }
            coEvery { database.getArticleDao().searchCars(query) } returns Single.just(list)

            //When
            val testObserver = sut.searchCarByManufacturer(query).test()

            //Then
            testObserver.run {
                assertSubscribed()
                assertNotNull(testObserver.values())
                assertEquals(1, testObserver.values().size)
                assertEquals(list[0].name, values()[0][0].name)
                assertEquals(list[1].name, values()[0][1].name)
            }
        }

    @Test
    fun `Given user searches with a irrelevant query, when query is checked with db values , then empty results are returned`() =
        runBlocking {
            //Given
            val query = "audi"
            val list = listOf<Manufacturer>()
            coEvery { database.getArticleDao().searchCars(query) } returns Single.just(list)

            //When
            val testObserver = sut.searchCarByManufacturer(query).test()

            //Then
            testObserver.run {
                assertSubscribed()
                assertNotNull(testObserver.values())
                assertTrue(testObserver.values()[0].isEmpty())
            }
        }
}



