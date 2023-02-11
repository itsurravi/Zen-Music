package com.ravisharma.zen_music.data.music

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravisharma.zen_music.Constants

@Entity(tableName = Constants.Tables.BLACKLIST_TABLE)
data class BlacklistedSong(
    @PrimaryKey val location: String,
    val title: String,
    val artist: String,
)
