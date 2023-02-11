package com.ravisharma.zen_music.nowplaying

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import com.ravisharma.zen_music.components.SongCardV2
import com.ravisharma.zen_music.data.music.Song
import kotlinx.coroutines.delay
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.Queue(
    queue: List<Song>,
    onFavouriteClicked: (Song) -> Unit,
    currentSong: Song?,
    onDownArrowClicked: () -> Unit,
    expanded: Boolean,
    exoPlayer: ExoPlayer,
    onDrag: (fromIndex: Int, toIndex: Int) -> Unit,
) {
    Icon(
        imageVector = Icons.Outlined.KeyboardArrowDown,
        contentDescription = "down arrow icon",
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
            .size(36.dp)
            .clickable(
                onClick = onDownArrowClicked,
                indication = rememberRipple(
                    bounded = true,
                    radius = 18.dp
                ),
                interactionSource = remember { MutableInteractionSource() }
            ),
        tint = MaterialTheme.colorScheme.onSecondaryContainer
    )
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = currentSong, key2 = expanded) {
        delay(600)
        if (!expanded) {
            listState.scrollToItem(exoPlayer.currentMediaItemIndex)
            return@LaunchedEffect
        }
        if (!listState.isScrollInProgress) {
            listState.animateScrollToItem(exoPlayer.currentMediaItemIndex)
        }
    }
    val dragDropState = rememberDragDropState(listState) { fromIndex, toIndex ->
        Timber.d("dd from:$fromIndex to:$toIndex")
        onDrag(fromIndex,toIndex)
        exoPlayer.moveMediaItem(fromIndex,toIndex)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .align(Alignment.CenterHorizontally)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .dragContainer(dragDropState),
        state = listState,
        contentPadding = WindowInsets.systemBars
            .only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)
            .asPaddingValues(),
    ) {
        itemsIndexed(
            items = queue,
            key = { _, song -> song.location }
        ) { index, song ->
            val isPlaying = currentSong?.location == song.location
            DraggableItem(dragDropState, index) {
                SongCardV2(
                    song = song,
                    onSongClicked = { if(!isPlaying){ exoPlayer.seekTo(index,0) } },
                    onFavouriteClicked = onFavouriteClicked,
                    currentlyPlaying = isPlaying,
                )
            }
        }
    }
}