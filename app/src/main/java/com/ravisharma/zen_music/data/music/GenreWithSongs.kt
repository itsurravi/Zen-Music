package com.ravisharma.zen_music.data.music

import androidx.room.Embedded
import androidx.room.Relation

data class GenreWithSongs(
    @Embedded
    val genre: Genre,
    @Relation(
        parentColumn = "genre",
        entityColumn = "genre"
    )
    val songs: List<Song>
)
