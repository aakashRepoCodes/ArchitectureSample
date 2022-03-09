package com.test.cars.presentation.viewmodel

import app.cash.turbine.test
import com.test.cars.TestCoroutineContextProvider
import com.test.cars.domain.model.Manufacturer
import com.test.cars.domain.usecase.GetManufacturerSearchUseCase
import com.test.cars.presentation.model.ManufacturerSearchState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ManufacturerSearchViewModelTest {

    @MockK
    private lateinit var useCaseManufacturer: GetManufacturerSearchUseCase

    private lateinit var sut: ManufacturerSearchViewModel

    private val testCoroutineContextProvider = TestCoroutineContextProvider()

    @Before
    fun init() {
        MockKAnnotations.init(this)
        sut = ManufacturerSearchViewModel(useCaseManufacturer, testCoroutineContextProvider)
    }

    @Test
    fun `Given user input some query when query is requested then viewmodel returns matching results`() {
        testCoroutineContextProvider.runBlockingTest {
            //Given
            val query = "B"
            val list = mutableListOf<Manufacturer>()
            list.add(Manufacturer("1", "Bmw"))
            list.add(Manufacturer("2", "Bugatti"))
            coEvery { useCaseManufacturer.invoke(query) } returns Single.just(list)

            //When
            sut.getSearchedCarsListData(query)

            //Then

            sut.searchState.test {
                assertTrue(expectMostRecentItem() is ManufacturerSearchState.MatchingResult)
                cancelAndConsumeRemainingEvents()
            }

        }
    }

    @Test
    fun `Given user input inavlid query when query is requested then viewmodel returns empty results`() {
        testCoroutineContextProvider.runBlockingTest {
            //Given
            val query = "+_)B"
            coEvery { useCaseManufacturer.invoke(query) } returns Single.just(emptyList())

            //When
            sut.getSearchedCarsListData(query)

            //Then

            sut.searchState.test {
                assertTrue(expectMostRecentItem() is ManufacturerSearchState.NoResult)
                cancelAndConsumeRemainingEvents()
            }

        }
    }

    @Test
    fun `Given user input query when query is requested and error happened then viewmodel returns error`() {
        testCoroutineContextProvider.runBlockingTest {
            //Given
            val query = "+_)B"
            coEvery { useCaseManufacturer.invoke(query) } returns Single.error(Exception())

            //When
            sut.getSearchedCarsListData(query)

            //Then

            sut.searchState.test {
                assertTrue(expectMostRecentItem() is ManufacturerSearchState.SearchError)
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
            sut.searchState.test {
                assertTrue(expectMostRecentItem() is ManufacturerSearchState.ClearSearchResult)
                cancelAndConsumeRemainingEvents()
            }

        }
    }

}

