package com.github.pakka_papad.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.pakka_papad.components.PlaylistCard
import com.github.pakka_papad.data.music.Playlist

@Composable
fun Playlists(
    paddingValues: PaddingValues,
    playlists: List<Playlist>,
    onPlaylistClicked: (Long) -> Unit,
    listState: LazyListState,
    onPlaylistCreate: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = listState,
        contentPadding = paddingValues,
    ) {
        item {
            CreatePlaylistCard(
                onPlaylistCreate = onPlaylistCreate,
            )
        }
        items(
            items = playlists,
            key = { it.playlistId }
        ) { playlist ->
            PlaylistCard(
                playlist = playlist,
                onPlaylistClicked = onPlaylistClicked
            )
        }
    }
}

@Composable
fun CreatePlaylistCard(
    onPlaylistCreate: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(12.dp)
    ) {
        var isDialogVisible by remember { mutableStateOf(false) }
        var playlistName by remember { mutableStateOf("") }
        Button(
            onClick = {
                isDialogVisible = true
            },
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center)
        ) {
            Text(
                text = "Create new playlist",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        if (isDialogVisible) {
            AlertDialog(
                onDismissRequest = { isDialogVisible = false },
                confirmButton = {
                    Button(
                        onClick = {
                            isDialogVisible = false
                            onPlaylistCreate(playlistName)
                            playlistName = ""
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                title = {
                    Text(text = "Playlist name")
                },
                text = {
                    TextField(
                        value = playlistName,
                        onValueChange = {
                            playlistName = it
                        },
                    )
                }
            )
        }
    }
}