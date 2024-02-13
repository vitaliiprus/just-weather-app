package prus.justweatherapp.feature.locations.search

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.feature.locations.model.SearchLocationUiModel

sealed interface SearchLocationScreenState {
    data object Loading : SearchLocationScreenState
    data class Error(val message: String) : SearchLocationScreenState
    data class Success(
        val locations: Flow<PagingData<SearchLocationUiModel>>
    ) : SearchLocationScreenState
}