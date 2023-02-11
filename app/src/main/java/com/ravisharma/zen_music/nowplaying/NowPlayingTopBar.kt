package com.ravisharma.zen_music.nowplaying

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.ravisharma.zen_music.components.TopBarWithBackArrow
import com.ravisharma.zen_music.components.more_options.OptionsDropDown

@Composable
fun NowPlayingTopBar(
    onBackArrowPressed: () -> Unit,
    title: String,
    options: List<NowPlayingOptions> = listOf(),
) = TopBarWithBackArrow(
    onBackArrowPressed = onBackArrowPressed,
    title = title,
    actions = {
        var dropDownMenuExpanded by remember { mutableStateOf(false) }
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .size(30.dp)
                .rotate(90f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(
                        bounded = false,
                        radius = 25.dp,
                    ),
                    onClick = { dropDownMenuExpanded = true }
                ),
            tint = MaterialTheme.colorScheme.onSurface,
        )
        OptionsDropDown(
            options = options,
            expanded = dropDownMenuExpanded,
            onDismissRequest = { dropDownMenuExpanded = false },
            offset = DpOffset(x = 0.dp, y = (-46).dp)
        )
    }
)