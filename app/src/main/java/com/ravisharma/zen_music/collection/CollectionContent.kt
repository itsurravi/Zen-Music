package com.ravisharma.zen_music.collection

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.ravisharma.zen_music.components.FullScreenSadMessage
import com.ravisharma.zen_music.components.SongCardV1
import com.ravisharma.zen_music.components.more_options.SongOptions
import com.ravisharma.zen_music.data.music.Song
import com.ravisharma.zen_music.home.PlayShuffleCard
import com.ravisharma.zen_music.home.SongInfo

@Composable
fun CollectionContent(
    paddingValues: PaddingValues,
    songs: List<Song>,
    songsListState: LazyListState,
    onSongClicked: (index: Int) -> Unit,
    onSongFavouriteClicked: (Song) -> Unit,
    currentSong: Song?,
    onAddToQueueClicked: (Song) -> Unit,
    onPlayAllClicked: () -> Unit,
    onShuffleClicked: () -> Unit,
    onAddToPlaylistsClicked: (Song) -> Unit,
    isPlaylistCollection: Boolean,
    onRemoveFromPlaylistClicked: (Song) -> Unit,
) {
    if (songs.isEmpty()) {
        FullScreenSadMessage(
            message = "No songs found",
            paddingValues = paddingValues
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = songsListState,
            contentPadding = paddingValues
        ) {
            item {
                PlayShuffleCard(
                    onPlayAllClicked = onPlayAllClicked,
                    onShuffleClicked = onShuffleClicked,
                )
            }
            itemsIndexed(
                items = songs,
                key = { index, song ->
                    song.location
                }
            ) { index, song ->
                var infoVisible by remember { mutableStateOf(false) }
                SongCardV1(
                    song = song,
                    onSongClicked = {
                        onSongClicked(index)
                    },
                    onFavouriteClicked = onSongFavouriteClicked,
                    songOptions = listOf(
                        SongOptions.Info { infoVisible = true },
                        SongOptions.AddToQueue { onAddToQueueClicked(song) },
                        SongOptions.AddToPlaylist { onAddToPlaylistsClicked(song) },
                    ) + if (isPlaylistCollection) listOf(
                        SongOptions.RemoveFromPlaylist { onRemoveFromPlaylistClicked(song) }
                    ) else listOf(),
                    currentlyPlaying = (song.location == currentSong?.location)
                )
                if (infoVisible) {
                    SongInfo(song) { infoVisible = false }
                }
            }
        }
    }
}