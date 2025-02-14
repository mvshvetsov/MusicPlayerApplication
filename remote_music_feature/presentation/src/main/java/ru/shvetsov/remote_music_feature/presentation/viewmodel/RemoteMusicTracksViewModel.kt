package ru.shvetsov.remote_music_feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.shvetsov.common.utils.NetworkResult
import ru.shvetsov.common.utils.UIText
import ru.shvetsov.remote_music_feature.domain.use_cases.FetchRemoteMusicTracksUseCase
import ru.shvetsov.remote_music_feature.domain.use_cases.SearchRemoteMusicTracksUseCase
import ru.shvetsov.track_list_common.event.MusicTrackEvent
import ru.shvetsov.track_list_common.state.MusicTrackUiState
import javax.inject.Inject

@HiltViewModel
class RemoteMusicTracksViewModel @Inject constructor(
    private val fetchRemoteMusicTracksUseCase: FetchRemoteMusicTracksUseCase,
    private val searchRemoteMusicTracksUseCase: SearchRemoteMusicTracksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MusicTrackUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: MusicTrackEvent) {
        when (event) {
            is MusicTrackEvent.FetchTracks -> {
                fetchRemoteMusicTracks()
            }

            is MusicTrackEvent.SearchTracks -> {
                searchRemoteMusicTracks(event.query)
            }
        }
    }

    private fun fetchRemoteMusicTracks() = executeUseCase(
        useCase = { fetchRemoteMusicTracksUseCase.invoke() },
        updateUiState = { isLoading, error, data ->
            _uiState.update {
                it.copy(
                    isLoading = isLoading,
                    error = error ?: UIText.EmptyString,
                    data = data ?: it.data
                )
            }
        }
    )

    private fun searchRemoteMusicTracks(query: String) = executeUseCase(
        useCase = { searchRemoteMusicTracksUseCase.invoke(query) },
        updateUiState = { isLoading, error, data ->
            _uiState.update {
                it.copy(
                    isLoading = isLoading,
                    error = error ?: UIText.EmptyString,
                    data = data ?: emptyList()
                )
            }
        }
    )

    private fun <T> executeUseCase(
        useCase: suspend () -> Flow<NetworkResult<T>>,
        updateUiState: (isLoading: Boolean, error: UIText?, data: T?) -> Unit
    ) {
        viewModelScope.launch {
            useCase().onEach { result ->
                when (result) {
                    is NetworkResult.Loading -> updateUiState(true, null, null)
                    is NetworkResult.Error -> updateUiState(
                        false,
                        UIText.RemoteString(result.message.toString()),
                        null
                    )
                    is NetworkResult.Success -> updateUiState(false, null, result.data)
                }
            }.launchIn(viewModelScope)
        }
    }
}