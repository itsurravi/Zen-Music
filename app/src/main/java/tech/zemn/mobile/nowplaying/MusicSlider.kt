package tech.zemn.mobile.nowplaying

import android.widget.SeekBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import tech.zemn.mobile.R
import tech.zemn.mobile.toMS
import kotlin.time.Duration.Companion.seconds

@Composable
fun MusicSlider(
    modifier: Modifier,
    mediaPlayer: ExoPlayer,
    duration: Long,
) {
    var currentValue by remember { mutableStateOf(0L) }
    var isPlaying by remember { mutableStateOf(mediaPlayer.isPlaying) }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying_: Boolean) {
                isPlaying = isPlaying_
            }
        }
        mediaPlayer.addListener(listener)
        onDispose {
            mediaPlayer.removeListener(listener)
        }
    }
    if (isPlaying) {
        LaunchedEffect(Unit) {
            while (true) {
                currentValue = mediaPlayer.currentPosition
                delay(1.seconds / 30)
            }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AndroidView(
            factory = { context ->
                SeekBar(context).apply {
                    setOnSeekBarChangeListener(
                        object : SeekBar.OnSeekBarChangeListener {
                            override fun onProgressChanged(
                                seekBar: SeekBar?,
                                progress: Int,
                                fromUser: Boolean
                            ) {
                                if (fromUser) {
                                    currentValue = progress.toLong()
                                }
                            }

                            override fun onStartTrackingTouch(seekBar: SeekBar?) {

                            }

                            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                                mediaPlayer.seekTo(currentValue)
                            }
                        }
                    )
                    thumb = resources.getDrawable(R.drawable.seekbar_thumb, null)
                    progressDrawable = resources.getDrawable(R.drawable.progress, null)
                    max = duration.toInt()
                }
            },
            update = { seekBar ->
                seekBar.progress = currentValue.toInt()
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = currentValue.toMS(),
                fontSize = 14.sp,
            )
            Text(
                text = duration.toMS(),
                fontSize = 14.sp,
            )
        }
    }
}
