package com.test.cars.domain

import com.test.cars.domain.model.Manufacturer
import com.test.cars.domain.repository.AutoMobileRepository
import com.test.cars.domain.usecase.GetManufacturerSearchUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetManufacturerSearchUseCaseTest {

    @MockK
    private lateinit var repository: AutoMobileRepository

    private lateinit var sut: GetManufacturerSearchUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
        sut = GetManufacturerSearchUseCase(repository)
    }

    @Test
    fun `Given user input some query when query is requested to db then matching results are shown`() =runBlocking {
        //Given
        val query = "bmw"
        val result = mutableListOf<Manufacturer>()
        result.add(Manufacturer("1","Bmw"))
        result.add(Manufacturer("2","Bugatti"))
        coEvery { repository.searchCarByManufacturer(query) } returns Single.just(result)

        //When
        val testObserver = sut.invoke(query).test()

        //Then
        testObserver.run {
            assertSubscribed()
            assertNotNull(testObserver.values())
            assertEquals(1, testObserver.values().size)
            assertEquals(result[0].name, values()[0][0].name)
            assertEquals(result[1].name, values()[0][1].name)
        }

    }

    @Test
    fun `Given user input some query which has no matching results when query is requested to db then empty results are shown`() =runBlocking {
        //Given
        val query = "ufkgewfgewkufkefh"
        val result = listOf<Manufacturer>()
        coEvery { repository.searchCarByManufacturer(query) } returns Single.just(result)

        //When
        val testObserver = sut.invoke(query).test()

        //Then
        testObserver.run {
            assertSubscribed()
            assertEquals(result.size, values()[0].size)
        }

    }

}