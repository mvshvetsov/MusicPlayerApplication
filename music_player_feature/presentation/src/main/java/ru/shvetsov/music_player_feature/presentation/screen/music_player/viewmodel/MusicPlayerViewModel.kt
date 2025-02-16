package ru.shvetsov.music_player_feature.presentation.screen.music_player.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.shvetsov.common.enums.TrackSource
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.common.utils.NetworkResult
import ru.shvetsov.common.utils.UIText
import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromApiUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromLocalStorageUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PauseTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PlayNextTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PlayPreviousTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.PlayTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.ResumeTrackUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.SeekToUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.SendTrackToServiceUseCase
import ru.shvetsov.music_player_feature.presentation.R
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val getTrackFromApiUseCase: GetTrackFromApiUseCase,
    private val getTrackFromLocalStorageUseCase: GetTrackFromLocalStorageUseCase,
    private val pauseTrackUseCase: PauseTrackUseCase,
    private val playNextTrackUseCase: PlayNextTrackUseCase,
    private val playPreviousTrackUseCase: PlayPreviousTrackUseCase,
    private val playTrackUseCase: PlayTrackUseCase,
    private val resumeTrackUseCase: ResumeTrackUseCase,
    private val seekToUseCase: SeekToUseCase,
    private val sendTrackToServiceUseCase: SendTrackToServiceUseCase,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(MusicPlayer.UiState())
    val uiState = _uiState.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0L)
    val progress = _progress.asStateFlow()

    private val listMusicTrack: List<MusicTrack> =
        Gson().fromJson(savedStateHandle.get<String>("trackList"), Array<MusicTrack>::class.java)
            .toList()

    private var selectedTrackIndex: Int =
        savedStateHandle["selectedTrackIndex"] ?: 0

    private val _elapsedTime = MutableStateFlow("00:00")
    val elapsedTime = _elapsedTime.asStateFlow()

    private val _trackDuration = MutableStateFlow("00:00")
    val trackDuration = _trackDuration.asStateFlow()

    fun onEvent(event: MusicPlayer.Event) {
        when (event) {

            is MusicPlayer.Event.LoadTrack -> {
                loadTrack()
            }

            is MusicPlayer.Event.PauseTrack -> {
                pauseTrack()
            }

            is MusicPlayer.Event.ResumeTrack -> {
                resumeTrack()
            }

            is MusicPlayer.Event.NextTrack -> {
                playNextTrack()
            }

            is MusicPlayer.Event.PreviousTrack -> {
                playPreviousTrack()
            }

            is MusicPlayer.Event.SeekTo -> {
                seekTo(event.position)
            }
        }
    }


    private fun getTrackFromApi(id: Long) = getTrackFromApiUseCase.invoke(id)
        .onEach { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    _uiState.update { MusicPlayer.UiState(isLoading = true) }
                }

                is NetworkResult.Error -> {
                    _uiState.update { MusicPlayer.UiState(error = UIText.RemoteString(result.message.toString())) }
                }

                is NetworkResult.Success -> {
                    _uiState.update { MusicPlayer.UiState(data = result.data) }
                    result.data?.let {
                        val durationMs =  it.duration!! * 1000L
                        _trackDuration.value = formatDuration(durationMs)
                        playTrack(it.trackPath.toString())
                        sendTrack(it)
                    }
                }
            }
        }.launchIn(viewModelScope)

    private fun getTrackFromLocalStorage(id: Long) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            val track = getTrackFromLocalStorageUseCase.invoke(id)
            _uiState.update { it.copy(isLoading = false, data = track) }
            track?.let {
                _trackDuration.value = formatDuration(it.duration!!.toLong())
            }
            playTrack(track?.trackPath.toString())
            sendTrack(track!!)
        } catch (e: Exception) {
            Log.d("local_track", "Exception: ${e.message}")
            _uiState.update {
                it.copy(
                    isLoading = false,
                    error = UIText.StringResource(R.string.something_went_wrong)
                )
            }
        }
    }

    private fun sendTrack(track: MusicTrackModel) {
        sendTrackToServiceUseCase.invoke(track)
    }

    private fun loadTrack() {
        val track = listMusicTrack[selectedTrackIndex]
        when (track.source) {
            TrackSource.API -> getTrackFromApi(track.id ?: 0)
            TrackSource.LOCAL -> getTrackFromLocalStorage(track.id ?: 0)
        }
    }

    private fun playTrack(uri: String) {
        playTrackUseCase.invoke(uri)
    }

    private fun pauseTrack() {
        pauseTrackUseCase.invoke()
        _isPlaying.value = false
    }

    private fun resumeTrack() {
        resumeTrackUseCase.invoke()
        _isPlaying.value = true
    }

    private fun seekTo(position: Long) {
        seekToUseCase.invoke(position)
    }

    private fun playNextTrack() {
        selectedTrackIndex = (selectedTrackIndex + 1) % listMusicTrack.size
        val track = listMusicTrack[selectedTrackIndex]
        when (track.source) {
            TrackSource.API -> getTrackFromApi(track.id ?: 0)
            TrackSource.LOCAL -> getTrackFromLocalStorage(track.id ?: 0)
        }
    }

    private fun playPreviousTrack() {
        selectedTrackIndex = (selectedTrackIndex - 1 + listMusicTrack.size) % listMusicTrack.size
        val track = listMusicTrack[selectedTrackIndex]
        when (track.source) {
            TrackSource.API -> getTrackFromApi(track.id ?: 0)
            TrackSource.LOCAL -> getTrackFromLocalStorage(track.id ?: 0)
        }
    }

    private fun formatDuration(durationMs: Long): String {
        val totalSeconds = durationMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}

object MusicPlayer {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UIText = UIText.EmptyString,
        val data: MusicTrackModel? = null
    )

    sealed interface Event {
        data object LoadTrack : Event
        data object PauseTrack : Event
        data object ResumeTrack : Event
        data object NextTrack : Event
        data object PreviousTrack : Event
        data class SeekTo(val position: Long) : Event
    }
}