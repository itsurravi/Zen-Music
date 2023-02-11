package com.ravisharma.zen_music.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ravisharma.zen_music.components.more_options.OptionsAlertDialog
import com.ravisharma.zen_music.components.more_options.PlaylistOptions
import com.ravisharma.zen_music.data.music.PlaylistWithSongCount

@Composable
private fun BasePlaylistCard(
    onCardClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(12.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .clickable(onClick = onCardClicked)
        .padding(horizontal = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    content = content
)

@Composable
private fun PlaylistName(
    playlist: PlaylistWithSongCount,
) = Text(
    text = playlist.playlistName,
    color = MaterialTheme.colorScheme.onSecondaryContainer,
    modifier = Modifier.background(Color.Transparent),
    style = MaterialTheme.typography.titleMedium
)

@Composable
fun SelectablePlaylistCard(
    playlist: PlaylistWithSongCount,
    isSelected: Boolean,
    onSelectChange: () -> Unit,
) = BasePlaylistCard(
    onCardClicked = onSelectChange,
    content = {
        PlaylistName(
            playlist = playlist,
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "Check mark",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
)

@Composable
fun PlaylistCard(
    playlistWithSongCount: PlaylistWithSongCount,
    onPlaylistClicked: (Long) -> Unit,
    options: List<PlaylistOptions> = listOf(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable(onClick = { onPlaylistClicked(playlistWithSongCount.playlistId) })
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = playlistWithSongCount.playlistName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${playlistWithSongCount.count} ${if (playlistWithSongCount.count == 1) "song" else "songs"}",
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
        }
        var optionsVisible by remember { mutableStateOf(false) }
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null,
            modifier = Modifier
                .size(26.dp)
                .clickable(
                    onClick = {
                        optionsVisible = true
                    },
                    indication = rememberRipple(
                        bounded = false,
                        radius = 20.dp
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
        )
        if (optionsVisible) {
            OptionsAlertDialog(
                options = options,
                title = playlistWithSongCount.playlistName,
                onDismissRequest = { optionsVisible = false }
            )
        }
    }
}

