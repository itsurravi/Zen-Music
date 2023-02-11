package com.ravisharma.zen_music.collection

import com.ravisharma.zen_music.data.music.Song

data class CollectionUi(
    val error: String? = null,
    val songs: List<Song> = listOf(),
    val topBarTitle: String = "",
    val topBarBackgroundImageUri: String = "",
)
