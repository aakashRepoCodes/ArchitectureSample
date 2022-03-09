package com.test.cars.domain

import androidx.paging.PagingData
import com.test.cars.domain.repository.AutoMobileRepository
import com.test.cars.domain.usecase.GetManufacturersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetAllManufacturerUseCaseTest {

    @MockK
    private lateinit var repository: AutoMobileRepository

    private lateinit var sut: GetManufacturersUseCase

    @Before
    fun init() {
        MockKAnnotations.init(this)
        sut = GetManufacturersUseCase(repository)
    }

    @Test
    fun `Given user opens home page when app calls to remote for manufacturer list then desired results are shown`() =runBlocking {
        //Given
        val map = mutableMapOf<String, String>().apply {
            put("1", "Bmw")
            put("2", "Audi")
        }
        val pagedList = PagingData.from(map.toList())
        coEvery { repository.getManufacturerList() } returns flowOf(pagedList)

        //When
        val result = sut.invoke()

        //Then
        result.run {
            assertNotNull(this)
            collectLatest {
                assertEquals(it, pagedList)
            }
        }
    }
}