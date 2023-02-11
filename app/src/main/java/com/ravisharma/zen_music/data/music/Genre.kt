package com.ravisharma.zen_music.data.music

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravisharma.zen_music.Constants

@Entity(tableName = Constants.Tables.GENRE_TABLE)
data class Genre(
    @PrimaryKey val genre: String,
)
