package com.ravisharma.zen_music.data.music

data class PlaylistWithSongCount(
    val playlistId: Long,
    val playlistName: String,
    val createdAt: Long,
    val count: Int = 0,
)
