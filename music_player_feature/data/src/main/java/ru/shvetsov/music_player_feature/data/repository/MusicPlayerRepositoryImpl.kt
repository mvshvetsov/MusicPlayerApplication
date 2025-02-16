package ru.shvetsov.music_player_feature.data.repository

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.shvetsov.common.enums.TrackSource
import ru.shvetsov.common.utils.BaseApiResponse
import ru.shvetsov.music_player_feature.data.mappers.toMusicTrackModel
import ru.shvetsov.music_player_feature.data.remote.MusicPlayerService
import ru.shvetsov.music_player_feature.data.service.MusicService
import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel
import ru.shvetsov.music_player_feature.domain.repository.MusicPlayerRepository
import javax.inject.Inject

class MusicPlayerRepositoryImpl @Inject constructor(
    private val musicPlayerService: MusicPlayerService,
    @ApplicationContext private val context: Context
) : BaseApiResponse(), MusicPlayerRepository {

    override suspend fun getTrackFromApi(id: Long): Result<MusicTrackModel> {
        return handleApiCall(
            api = { musicPlayerService.getTrackById(id) },
            mapper = { response ->
                response.toMusicTrackModel() ?: throw Exception("Track not found")
            }
        )
    }

    override fun getTrackFromLocalStorage(id: Long): MusicTrackModel? {
        val queryUri = if (Build.VERSION.SDK_INT >= 29) {
            MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else MediaStore.Audio.Media.getContentUri("external")

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Albums.ALBUM
        )

        val selection = "${MediaStore.Audio.Media._ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        return context.contentResolver.query(
            queryUri, projection, selection, selectionArgs, null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val albumTitleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)

                val trackId = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)
                val albumTitle = cursor.getString(albumTitleColumn)

                val albumUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId
                ).toString()
                val trackUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, trackId
                ).toString()

                MusicTrackModel(trackId, title, artist, albumUri, albumTitle, duration, trackUri, TrackSource.LOCAL)
            } else {
                null
            }
        }
    }

    override fun playTrack(uri: String) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_PLAY
            putExtra(MusicService.EXTRA_TRACK_URI, uri)
        }
        context.startService(intent)
    }

    override fun pauseTrack() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_PAUSE
        }
        context.startService(intent)
    }

    override fun resumeTrack() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_RESUME
        }
        context.startService(intent)
    }

    override fun seekTo(position: Long) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_SEEK_TO
            putExtra(MusicService.EXTRA_SEEK_POSITION, position)
        }
        context.startService(intent)
    }

    override fun playNextTrack() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_NEXT
        }
        context.startService(intent)
    }

    override fun playPreviousTrack() {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_PREVIOUS
        }
        context.startService(intent)
    }

    override fun sendTrackToService(track: MusicTrackModel) {
        val intent = Intent(context, MusicService::class.java).apply {
            action = MusicService.ACTION_PLAY
            putExtra(MusicService.EXTRA_TRACK, track)
        }
        context.startService(intent)
    }
}