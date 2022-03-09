package com.test.cars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.cars.data.common.CoroutineContextProvider
import com.test.cars.domain.usecase.GetManufacturerSearchUseCase
import com.test.cars.presentation.model.ManufacturerSearchState
import com.test.cars.presentation.model.ManufacturerSearchState.*
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManufacturerSearchViewModel(
    private val getManufacturerSearchUseCase: GetManufacturerSearchUseCase,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private val _searchState = MutableStateFlow<ManufacturerSearchState>(Loading)
    val searchState: StateFlow<ManufacturerSearchState> get() = _searchState

    fun getSearchedCarsListData(query: String) {
        viewModelScope.launch(coroutineContextProvider.io) {
            getManufacturerSearchUseCase.invoke(query)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _searchState.value = if (it.isEmpty()) NoResult else MatchingResult(it)
                }, {
                    _searchState.value = SearchError
                })
        }
    }

    fun resetSearch() {
        _searchState.value = ClearSearchResult
    }
}