package ru.shvetsov.remote_music_feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.shvetsov.common.utils.NetworkResult
import ru.shvetsov.common.utils.UIText
import ru.shvetsov.remote_music_feature.domain.use_cases.FetchRemoteMusicTracksUseCase
import ru.shvetsov.remote_music_feature.domain.use_cases.SearchRemoteMusicTracksUseCase
import ru.shvetsov.track_list_common.model.MusicTrack
import javax.inject.Inject

class RemoteMusicTracksViewModel @Inject constructor(
    private val fetchRemoteMusicTracksUseCase: FetchRemoteMusicTracksUseCase,
    private val searchRemoteMusicTracksUseCase: SearchRemoteMusicTracksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemoteMusicTrack.UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RemoteMusicTrack.Event) {
        when (event) {
            is RemoteMusicTrack.Event.FetchRemoteMusicTracks -> {
                fetchRemoteMusicTracks()
            }

            is RemoteMusicTrack.Event.SearchRemoteTracks -> {
                searchRemoteMusicTracks(event.query)
            }
        }
    }

    private fun fetchRemoteMusicTracks() = viewModelScope.launch {
        fetchRemoteMusicTracksUseCase.invoke()
            .onEach { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _uiState.update {
                            RemoteMusicTrack.UiState(isLoading = true)
                        }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            RemoteMusicTrack.UiState(error = UIText.RemoteString(result.message.toString()))
                        }
                    }

                    is NetworkResult.Success -> {
                        _uiState.update {
                            RemoteMusicTrack.UiState(data = result.data)
                        }
                    }
                }
            }
    }

    private fun searchRemoteMusicTracks(query: String) = viewModelScope.launch {
        searchRemoteMusicTracksUseCase.invoke(query)
            .onEach { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _uiState.update { RemoteMusicTrack.UiState(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update {
                            RemoteMusicTrack.UiState(
                                error = UIText.RemoteString(
                                    result.message.toString()
                                )
                            )
                        }
                    }

                    is NetworkResult.Success -> {
                        _uiState.update {
                            RemoteMusicTrack.UiState(data = result.data)
                        }
                    }
                }
            }
    }
}

object RemoteMusicTrack {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UIText = UIText.EmptyString,
        val data: List<MusicTrack>? = null
    )

    sealed interface Event {
        data object FetchRemoteMusicTracks : Event
        data class SearchRemoteTracks(val query: String) : Event
    }
}