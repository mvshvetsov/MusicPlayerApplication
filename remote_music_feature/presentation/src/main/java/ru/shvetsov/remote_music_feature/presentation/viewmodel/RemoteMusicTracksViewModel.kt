package ru.shvetsov.remote_music_feature.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private fun fetchRemoteMusicTracks() = viewModelScope.launch {
        fetchRemoteMusicTracksUseCase.invoke()
            .onEach { result ->
                _uiState.update {
                    when (result) {
                        is NetworkResult.Loading -> it.copy(isLoading = true)
                        is NetworkResult.Error -> it.copy(
                            isLoading = false,
                            error = UIText.RemoteString(result.message.toString())
                        )

                        is NetworkResult.Success -> it.copy(isLoading = false, data = result.data)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchRemoteMusicTracks(query: String) = viewModelScope.launch {
        searchRemoteMusicTracksUseCase.invoke(query)
            .onEach { result ->
                _uiState.update {
                    when (result) {
                        is NetworkResult.Loading -> it.copy(isLoading = true)
                        is NetworkResult.Error -> it.copy(
                            isLoading = false,
                            error = UIText.RemoteString(result.message.toString())
                        )

                        is NetworkResult.Success -> {
                            Log.d("Search", "Search result: ${result.data}")
                            it.copy(isLoading = false, data = result.data)
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
}