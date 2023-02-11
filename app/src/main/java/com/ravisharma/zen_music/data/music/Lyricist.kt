package com.ravisharma.zen_music.data.music

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravisharma.zen_music.Constants

@Entity(tableName = Constants.Tables.LYRICIST_TABLE)
data class Lyricist(
    @PrimaryKey val name: String,
)
