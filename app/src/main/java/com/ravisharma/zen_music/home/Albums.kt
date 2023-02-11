package com.ravisharma.zen_music.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ravisharma.zen_music.components.FullScreenSadMessage
import com.ravisharma.zen_music.data.music.Album

@Composable
fun Albums(
    albums: List<Album>?,
    gridState: LazyGridState,
    onAlbumClicked: (Album) -> Unit
) {
    if (albums == null) return
    if (albums.isEmpty()) {
        FullScreenSadMessage(
            message = "Oops! No albums found",
            paddingValues = WindowInsets.systemBars.only(WindowInsetsSides.Bottom).asPaddingValues(),
        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize(),
            state = gridState,
            columns = GridCells.Adaptive(150.dp),
            contentPadding = WindowInsets.systemBars.only(WindowInsetsSides.Bottom).asPaddingValues(),
        ) {
            items(
                items = albums,
                key = { it.name }
            ) { album ->
                AlbumCard(
                    album = album,
                    onAlbumClicked = onAlbumClicked
                )
            }
        }
    }
}

@Composable
fun AlbumCard(
    album: Album,
    onAlbumClicked: (Album) -> Unit,
) {
    Column(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .fillMaxWidth()
            .clickable { onAlbumClicked(album) }
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = album.albumArtUri,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = false)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = album.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}