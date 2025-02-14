package ru.shvetsov.local_music_feture.data.repository

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.local_music_feature.domain.repository.LocalMusicTracksRepository
import javax.inject.Inject

class LocalMusicTracksRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalMusicTracksRepository {

    override fun fetchMusicTracks(): List<MusicTrack> {
        val musicTracks = mutableListOf<MusicTrack>()
        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION
        )

        context.contentResolver.query(
            queryUri,
            projection,
            null,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)
                val albumUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, albumId
                ).toString()
                musicTracks.add(MusicTrack(id, title, artist, albumUri, duration))
            }
        }
        return musicTracks
    }

    override fun searchMusicTracks(query: String): List<MusicTrack> {
        return fetchMusicTracks().filter {
            (it.title?.contains(query, ignoreCase = true) ?: false) ||
                    (it.artist?.contains(query, ignoreCase = true) ?: false)
        }
    }
}