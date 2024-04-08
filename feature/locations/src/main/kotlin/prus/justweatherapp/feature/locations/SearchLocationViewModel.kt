package prus.justweatherapp.feature.locations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import prus.justweatherapp.domain.locations.usecase.GetLocationsUseCase
import prus.justweatherapp.feature.locations.mapper.mapToSearchUiModel
import prus.justweatherapp.feature.locations.search.SearchLocationScreenState
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    var state: SearchLocationScreenState = SearchLocationScreenState.Loading

    init {
        viewModelScope.launch {
            searchQuery.collect { query ->
                state = SearchLocationScreenState.Success(
                    locations = getLocationsUseCase(query)
                        .map { pagingData ->
                            pagingData.map {
                                it.mapToSearchUiModel(query)
                            }
                        }
                        .cachedIn(viewModelScope)
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query.trim()
    }

    fun onSearchFocused() {
        savedStateHandle[SEARCH_QUERY] = ""
    }

    fun onSearchCancelClicked() {
        savedStateHandle[SEARCH_QUERY] = ""
    }
}

private const val SEARCH_QUERY = "searchQuery"