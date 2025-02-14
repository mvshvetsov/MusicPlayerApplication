package ru.shvetsov.track_list_common.event

sealed interface MusicTrackEvent {
    data object FetchTracks : MusicTrackEvent
    data class SearchTracks(val query: String) : MusicTrackEvent
}