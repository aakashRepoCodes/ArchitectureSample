package com.test.cars.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.test.cars.TestCoroutineContextProvider
import com.test.cars.domain.usecase.GetManufacturersUseCase
import com.test.cars.observeForeverMock
import com.test.cars.rule.MockkTest
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ManufacturerHomeViewModelTest : MockkTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getManufacturersUseCase: GetManufacturersUseCase

    private val testCoroutineContextProvider = TestCoroutineContextProvider()

    private lateinit var sut: ManufacturerHomeViewModel


    @Before
    fun init() {
        MockKAnnotations.init(this)
        sut = ManufacturerHomeViewModel(getManufacturersUseCase)
    }

    @Test
    fun `Given home page loads when data is requested then viewmodel returns all car list`() {
        testCoroutineContextProvider.runBlockingTest {
            //Given
            val map = mutableMapOf<String, String>().apply {
                put("1", "Bmw")
                put("2", "Audi")
            }
            val pagedList = PagingData.from(map.toList())
            val pageData = flowOf(pagedList)
            every { getManufacturersUseCase.invoke() } returns pageData

            //When
            val sut = ManufacturerHomeViewModel(getManufacturersUseCase)
            val observer = sut.allCarListDataState.asLiveData().observeForeverMock()

            //Then
            verify(exactly = 1) {
                observer.onChanged(
                   any()
                )
            }
        }
    }

}

