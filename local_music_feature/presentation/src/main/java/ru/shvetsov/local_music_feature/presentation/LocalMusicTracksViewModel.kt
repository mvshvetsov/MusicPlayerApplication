package ru.shvetsov.local_music_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.shvetsov.common.utils.UIText
import ru.shvetsov.local_music_feature.domain.use_cases.FetchLocalMusicTracksUseCase
import ru.shvetsov.local_music_feature.domain.use_cases.SearchLocalMusicTracksUseCase
import ru.shvetsov.track_list_common.event.MusicTrackEvent
import ru.shvetsov.track_list_common.state.MusicTrackUiState
import javax.inject.Inject

@HiltViewModel
class LocalMusicTracksViewModel @Inject constructor(
    private val fetchLocalMusicTracksUseCase: FetchLocalMusicTracksUseCase,
    private val searchLocalMusicTracksUseCase: SearchLocalMusicTracksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MusicTrackUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: MusicTrackEvent) {
        when (event) {
            is MusicTrackEvent.FetchTracks -> {
                fetchLocalMusicTracks()
            }

            is MusicTrackEvent.SearchTracks -> {
                searchLocalMusicTracks(event.query)
            }
        }
    }

    private fun fetchLocalMusicTracks() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            val tracks = fetchLocalMusicTracksUseCase.invoke()
            _uiState.update { it.copy(isLoading = false, data = tracks) }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = UIText.StringResource(R.string.something_went_wrong)
                )
            }
        }
    }

    private fun searchLocalMusicTracks(query: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            val filteredTracks = searchLocalMusicTracksUseCase.invoke(query)
            _uiState.update { it.copy(isLoading = false, data = filteredTracks) }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = UIText.StringResource(R.string.something_went_wrong)
                )
            }
        }
    }
}