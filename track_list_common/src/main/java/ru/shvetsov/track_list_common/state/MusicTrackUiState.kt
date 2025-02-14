package ru.shvetsov.track_list_common.state

import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.common.utils.UIText

data class MusicTrackUiState (
    val isLoading: Boolean = false,
    val error: UIText? = UIText.EmptyString,
    val data: List<MusicTrack>? = null
)