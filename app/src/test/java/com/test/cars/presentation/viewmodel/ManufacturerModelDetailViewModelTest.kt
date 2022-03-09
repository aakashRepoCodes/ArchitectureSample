package com.test.cars.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.test.cars.TestCoroutineContextProvider
import com.test.cars.data.model.ManufacturerDTO
import com.test.cars.domain.usecase.GetManufacturerModelUseCase
import com.test.cars.presentation.model.ManufacturerModelSearchState
import com.test.cars.presentation.model.ManufacturerModelSearchState.MatchingResult
import com.test.cars.rule.RxImmediateSchedulerRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ManufacturerModelDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val schedulersRule = RxImmediateSchedulerRule()

    @MockK
    private lateinit var useCaseManufacturer: GetManufacturerModelUseCase

    private lateinit var sut: ManufacturerModelDetailViewModel

    private val testCoroutineContextProvider = TestCoroutineContextProvider()

    @Before
    fun init() {
        MockKAnnotations.init(this)
        sut = ManufacturerModelDetailViewModel(useCaseManufacturer, testCoroutineContextProvider)
    }


    @Test
    fun `Given user selected manufacturer when models list is requested then viewmodel returns correct results`() {
        testCoroutineContextProvider.runBlockingTest {
            //Given
            val manufacturerId = "130"
            val map = mutableMapOf<String, String>().apply {
                put("1", "Bmw")
                put("2", "Audi")
            }
            val manufacturerDTO = mockk<ManufacturerDTO>()
            every { manufacturerDTO.list } returns map
            coEvery { useCaseManufacturer.invoke(manufacturerId) } returns Single.just(manufacturerDTO)

            //When
            sut.getAllModelList(manufacturerId)

            //Then
            sut.modelState.test {
                assertTrue(expectMostRecentItem() is MatchingResult)
                cancelAndConsumeRemainingEvents()
            }

        }
    }

    @Test
    fun `Given manufacturerId is invalid when model list is fetched then error happened then viewmodel returns error`() {
        testCoroutineContextProvider.runBlockingTest {
            //Given
            val id = "45"
            every { useCaseManufacturer.invoke(id) } returns Single.error(Exception())

            //When
            sut.getAllModelList(id)

            //Then

            sut.modelState.test {
                assertTrue(expectMostRecentItem() is ManufacturerModelSearchState.SearchError)
                cancelAndConsumeRemainingEvents()
            }

        }
    }

    @Test
    fun `when user clears query then viewmodel emits ClearSearchResult event`() {
        testCoroutineContextProvider.runBlockingTest {
            //When
            sut.resetSearch()

            //Then
            sut.modelState.test {
                assertTrue(expectMostRecentItem() is ManufacturerModelSearchState.ClearSearchResult)
                cancelAndConsumeRemainingEvents()
            }

        }
    }

}
