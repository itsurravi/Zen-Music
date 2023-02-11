package com.ravisharma.zen_music.select_playlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ravisharma.zen_music.components.SelectablePlaylistCard
import com.ravisharma.zen_music.data.music.PlaylistWithSongCount

@Composable
fun SelectPlaylistContent(
    playlists: List<PlaylistWithSongCount>,
    selectList: List<Boolean>,
    paddingValues: PaddingValues,
    onSelectChanged: (index: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = paddingValues,
    ) {
        itemsIndexed(
            items = playlists,
            key = { index, playlist -> playlist.playlistId }
        ) { index, playlist ->
            SelectablePlaylistCard(
                playlist = playlist,
                isSelected = selectList[index],
                onSelectChange = {
                    onSelectChanged(index)
                }
            )
        }
    }
}