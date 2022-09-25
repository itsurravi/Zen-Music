package tech.zemn.mobile.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.zemn.mobile.data.music.Song

@Composable
fun AllSongs(
    songs: List<Song>,
    onSongClicked: (Song) -> Unit,
    paddingValues: PaddingValues
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding()),
        state = listState
    ) {
        items(songs) { song ->
            SongCard(
                song = song,
                onSongClicked = onSongClicked
            )
        }
    }
}

@Composable
fun SongCard(
    song: Song,
    onSongClicked: (Song) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .clickable(
                onClick = {
                    onSongClicked(song)
                }
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .padding(start = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = "${song.artist}  •  ${song.duration}",
                    maxLines = 1,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(
                            onClick = {

                            },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 20.dp
                            ),
                            interactionSource = MutableInteractionSource()
                        )
                        .padding(8.dp)
                )
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(
                            onClick = {

                            },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 20.dp
                            ),
                            interactionSource = MutableInteractionSource()
                        )
                        .padding(8.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(0.8.dp)
                .background(color = Color.Black.copy(alpha = 0.1f))
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SongCardPreview() {
    SongCard(
        song = Song(
            location = "",
            title = "Shape of You",
            album = "",
            size = 0f,
            addedTimestamp = 0,
            modifiedTimestamp = 0,
            artist = "Ed Sheeran",
            albumArtist = "",
            composer = "",
            genre = "",
            lyricist = "",
            year = 0,
            comment = null,
            duration = 18000,
            bitrate = 0f,
            sampleRate = 0f,
            bitsPerSample = 0,
            mimeType = "audio/mpeg",
        )
    ) {

    }
}