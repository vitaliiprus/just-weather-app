package prus.justweatherapp.feature.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import prus.justweatherapp.core.common.result.Result
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.domain.locations.usecase.GetUserLocationsUseCase
import prus.justweatherapp.feature.locations.mapper.mapToUiModels
import prus.justweatherapp.feature.locations.user.UserLocationsScreenState
import javax.inject.Inject

@HiltViewModel
class UserLocationsViewModel @Inject constructor(
    getUserLocationsUseCase: GetUserLocationsUseCase
) : ViewModel() {

    var state: StateFlow<UserLocationsScreenState> =
        getUserLocationsUseCase()
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Error -> {
                        UserLocationsScreenState.Error(
                            result.exception.message ?: result.exception.toString()
                        )
                    }

                    Result.Loading -> {
                        UserLocationsScreenState.Loading
                    }

                    is Result.Success -> {
                        UserLocationsScreenState.Success(result.data.mapToUiModels())
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserLocationsScreenState.Loading,
            )

}