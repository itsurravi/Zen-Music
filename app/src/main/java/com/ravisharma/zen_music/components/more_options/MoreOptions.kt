package com.ravisharma.zen_music.components.more_options

import androidx.annotation.DrawableRes

open class MoreOptions(
    open val onClick: () -> Unit,
    open val text: String,
    @DrawableRes open val icon: Int,
)