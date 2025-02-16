package ru.shvetsov.track_list_common.adapter

import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.shvetsov.common.model.MusicTrack
import ru.shvetsov.track_list_common.screens.music_track.MusicTrackItem

class MusicTrackAdapter(
    private val onTrackClick: (Int, List<MusicTrack>) -> Unit
) : RecyclerView.Adapter<MusicTrackAdapter.ViewHolder>() {

    private var musicTracks: List<MusicTrack> = emptyList()

    fun submitList(newList: List<MusicTrack>) {
        val diffResult = DiffUtil.calculateDiff(MusicTrackDiffCallback(musicTracks, newList))
        musicTracks = newList.toList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicTrackAdapter.ViewHolder {
        val composeView = ComposeView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return ViewHolder(composeView)
    }

    override fun onBindViewHolder(holder: MusicTrackAdapter.ViewHolder, position: Int) {
        holder.bind(musicTracks[position], position, musicTracks)
    }

    override fun getItemCount(): Int = musicTracks.size

    inner class ViewHolder(private val composeView: ComposeView) :
        RecyclerView.ViewHolder(composeView) {
        fun bind(track: MusicTrack, position: Int, trackList: List<MusicTrack>) {
            composeView.setContent {
                MusicTrackItem(
                    musicTrack = track,
                    trackList = trackList,
                    selectedTrackIndex = position,
                    onTrackClick = onTrackClick
                )
            }
        }
    }
}

class MusicTrackDiffCallback(
    private val oldList: List<MusicTrack>,
    private val newList: List<MusicTrack>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}