package ru.shvetsov.music_player_feature.data.service

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import ru.shvetsov.music_player_feature.data.R
import ru.shvetsov.music_player_feature.domain.model.MusicTrackModel
import javax.inject.Inject

@AndroidEntryPoint
class MusicService : Service() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    private var currentTrack: MusicTrackModel? = null

    override fun onCreate() {
        super.onCreate()
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        initializePlayer()
    }

    private fun initializePlayer() {
        if (exoPlayer.playbackState == Player.STATE_IDLE) {
            exoPlayer.apply {
                addListener(object : Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        playbackState == Player.STATE_READY && playWhenReady
                    }
                })
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val track: MusicTrackModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(EXTRA_TRACK, MusicTrackModel::class.java)
                } else {
                    intent.getParcelableExtra(EXTRA_TRACK)
                }
                track?.let {
                    playTrack(track)
                }
            }
            ACTION_PAUSE -> pauseTrack()
            ACTION_RESUME -> resumeTrack()
            ACTION_SEEK_TO -> {
                val position = intent.getLongExtra(EXTRA_SEEK_POSITION, 0L)
                seekTo(position)
            }
            ACTION_NEXT -> playNextTrack()
            ACTION_PREVIOUS -> playPreviousTrack()
        }
        return START_STICKY
    }

    private fun sendNotification(track: MusicTrackModel) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(track.title)
            .setSmallIcon(R.drawable.note)
            .setContentText(track.artist)
            .addAction(
                R.drawable.previous,
                getString(R.string.previous),
                createPreviousPendingIntent()
            )
            .addAction(
                if (exoPlayer.isPlaying) R.drawable.pause else R.drawable.play_arrow,
                getString(R.string.play_pause),
                if (exoPlayer.isPlaying) createPlayPendingIntent() else createPausePendingIntent()
            )
            .addAction(R.drawable.next, getString(R.string.next), createNextPendingIntent())
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startForeground(1, notification)
            } else {
                startForeground(1, notification)
            }
        }
    }

    private fun createPreviousPendingIntent(): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            action = ACTION_PREVIOUS
        }
        return PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createPlayPendingIntent(): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            action = ACTION_PLAY
        }
        return PendingIntent.getService(
            this, 1, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createPausePendingIntent(): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            action = ACTION_PAUSE
        }
        return PendingIntent.getService(
            this, 2, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNextPendingIntent(): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply {
            action = ACTION_NEXT
        }
        return PendingIntent.getService(
            this, 3, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    private fun playTrack(track: MusicTrackModel) {
        currentTrack = track
        val mediaItem = MediaItem.fromUri(track.trackPath!!)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
        sendNotification(track)
    }

    private fun pauseTrack() {
        exoPlayer.pause()
        currentTrack?.let { sendNotification(it) }
    }

    private fun resumeTrack() {
        exoPlayer.play()
        currentTrack?.let { sendNotification(it) }
    }

    private fun seekTo(position: Long) {
        exoPlayer.seekTo(position)
    }

    private fun playNextTrack() {

    }

    private fun playPreviousTrack() {

    }

    companion object {
        const val CHANNEL_ID = "music_channel"
        const val CHANNEL_NAME = "music_channel"
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_SEEK_TO = "action_seek_to"
        const val ACTION_NEXT = "action_next"
        const val ACTION_PREVIOUS = "action_previous"
        const val ACTION_RESUME = "action_resume"
        const val EXTRA_TRACK_URI = "extra_track_uri"
        const val EXTRA_SEEK_POSITION = "extra_seek_position"
        const val EXTRA_TRACK = "EXTRA_TRACK"
    }
}