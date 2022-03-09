package com.test.cars.presentation.model

import com.test.cars.domain.model.Manufacturer

sealed class ManufacturerSearchState {
    object Loading: ManufacturerSearchState()
    object NoResult: ManufacturerSearchState()
    object ClearSearchResult: ManufacturerSearchState()
    object SearchError: ManufacturerSearchState()
    data class MatchingResult(val list: List<Manufacturer>) : ManufacturerSearchState()
}

