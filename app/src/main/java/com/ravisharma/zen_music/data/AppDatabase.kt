package com.ravisharma.zen_music.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ravisharma.zen_music.data.daos.*
import com.ravisharma.zen_music.data.music.*

@Database(entities = [
        Song::class,
        Album::class,
        Artist::class,
        Playlist::class,
        PlaylistSongCrossRef::class,
        Genre::class,
        AlbumArtist::class,
        Composer::class,
        Lyricist::class,
        BlacklistedSong::class
    ],
    version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {

    abstract fun songDao(): SongDao

    abstract fun albumDao(): AlbumDao

    abstract fun artistDao(): ArtistDao

    abstract fun albumArtistDao(): AlbumArtistDao

    abstract fun composerDao(): ComposerDao

    abstract fun lyricistDao(): LyricistDao

    abstract fun genreDao(): GenreDao

    abstract fun playlistDao(): PlaylistDao

    abstract fun blacklistDao(): BlacklistDao

}