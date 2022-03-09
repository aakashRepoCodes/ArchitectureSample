package com.test.cars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.cars.data.common.CoroutineContextProvider
import com.test.cars.domain.usecase.GetManufacturerModelUseCase
import com.test.cars.presentation.model.ManufacturerModelSearchState
import com.test.cars.presentation.model.ManufacturerModelSearchState.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManufacturerModelDetailViewModel(
    private val getManufacturerModelUseCse: GetManufacturerModelUseCase,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private val _modelState = MutableStateFlow<ManufacturerModelSearchState>(Loading)
    val modelState: StateFlow<ManufacturerModelSearchState> get() = _modelState

    private var searchedCarsList = mutableMapOf<String, String>()

    private var allCarModelsList = mutableMapOf<String, String>()

    fun getAllModelList(manufacturerId: String) {
        viewModelScope.launch(coroutineContextProvider.io) {
            getManufacturerModelUseCse.invoke(manufacturerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _modelState.value = MatchingResult(it.list.toList())
                    allCarModelsList.putAll(it.list)
                    searchedCarsList.putAll(it.list)
                }, {
                    _modelState.value = SearchError
                })
        }
    }

    fun searchCarByModel(modelName: String) {
        viewModelScope.launch(coroutineContextProvider.io) {
            searchedCarsList.filter {
                return@filter it.key.startsWith(modelName, ignoreCase = true)
            }.also {
                _modelState.emit(
                    if (it.isEmpty()) {
                        NoResult
                    } else {
                        MatchingResult(it.toList())
                    }
                )
            }
        }
    }

    fun resetSearch() {
        viewModelScope.launch(coroutineContextProvider.io) {
            _modelState.emit(ClearSearchResult(allCarModelsList.toList()))
        }
    }
}
