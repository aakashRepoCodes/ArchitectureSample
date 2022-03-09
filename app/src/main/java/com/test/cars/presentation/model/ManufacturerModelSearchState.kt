package com.test.cars.presentation.model

sealed class ManufacturerModelSearchState {
    object Loading : ManufacturerModelSearchState()
    object NoResult : ManufacturerModelSearchState()
    data class ClearSearchResult(val list: List<Pair<String, String>>) : ManufacturerModelSearchState()
    object SearchError : ManufacturerModelSearchState()
    data class MatchingResult(val list: List<Pair<String, String>>) : ManufacturerModelSearchState()
}