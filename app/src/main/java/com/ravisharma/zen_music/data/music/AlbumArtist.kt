package com.ravisharma.zen_music.data.music

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravisharma.zen_music.Constants

@Entity(tableName = Constants.Tables.ALBUM_ARTIST_TABLE)
data class AlbumArtist(
    @PrimaryKey val name: String
)
