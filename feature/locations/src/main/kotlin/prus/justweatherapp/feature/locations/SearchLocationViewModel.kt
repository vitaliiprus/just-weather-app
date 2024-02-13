package prus.justweatherapp.feature.locations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import prus.justweatherapp.core.common.result.Result
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.domain.locations.usecase.GetLocationsUseCase
import prus.justweatherapp.feature.locations.mapper.mapToSearchUiModel
import prus.justweatherapp.feature.locations.search.SearchLocationScreenState
import javax.inject.Inject

@HiltViewModel
class SearchLocationViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")

    @OptIn(ExperimentalCoroutinesApi::class)
    var state: StateFlow<SearchLocationScreenState> =
        searchQuery.flatMapLatest { query ->
            getLocationsUseCase(query)
                .map { pagingData ->
                    pagingData.map {
                        it.mapToSearchUiModel(query)
                    }
                }
                .cachedIn(viewModelScope)
                .asResult()
                .map { result ->
                    when (result) {
                        is Result.Error -> {
                            SearchLocationScreenState.Error(
                                result.exception.message ?: result.exception.toString()
                            )
                        }

                        Result.Loading -> {
                            SearchLocationScreenState.Loading
                        }

                        is Result.Success -> {
                            SearchLocationScreenState.Success(
                                locations = MutableStateFlow(result.data)
                            )
                        }

                    }
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchLocationScreenState.Loading,
        )

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }

    fun onSearchPressed() {

    }
}

private const val SEARCH_QUERY = "searchQuery"