package ru.shvetsov.music_player_feature.presentation.screen.music_player.viewmodel

import ru.shvetsov.common.utils.UIText
import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromApiUseCase
import ru.shvetsov.music_player_feature.domain.use_cases.GetTrackFromLocalStorageUseCase
import javax.inject.Inject

class MusicPlayerViewModel @Inject constructor(
    private val getTrackFromApiUseCase: GetTrackFromApiUseCase,
    private val getTrackFromLocalStorageUseCase: GetTrackFromLocalStorageUseCase
) {


}

object MusicPlayer {

    data class UiState(
        val isLoading: Boolean = false,
        val error: UIText = UIText.EmptyString,
        val data: MusicTrackModel? = null
    )

    sealed interface Event {

    }
}