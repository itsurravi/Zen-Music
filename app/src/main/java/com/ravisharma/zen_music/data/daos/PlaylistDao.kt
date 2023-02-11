package com.ravisharma.zen_music.data.daos

import androidx.room.*
import com.ravisharma.zen_music.Constants
import com.ravisharma.zen_music.data.music.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert(entity = Playlist::class)
    suspend fun insertPlaylist(playlist: PlaylistExceptId): Long

    @Delete(entity = Playlist::class)
    suspend fun deletePlaylist(playlist: Playlist)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlaylistSongCrossRef(playlistSongCrossRefs: List<PlaylistSongCrossRef>)

    @Delete
    suspend fun deletePlaylistSongCrossRef(playlistSongCrossRef: PlaylistSongCrossRef)

    @Query("SELECT * FROM ${Constants.Tables.PLAYLIST_TABLE}")
    fun getAllPlaylists(): Flow<List<Playlist>>

    @Transaction
    @Query("SELECT * FROM ${Constants.Tables.PLAYLIST_TABLE} WHERE playlistId = :playlistId")
    fun getPlaylistWithSongs(playlistId: Long): Flow<PlaylistWithSongs?>

    @Query("SELECT * FROM ${Constants.Tables.PLAYLIST_TABLE} WHERE playlistName LIKE '%' || :query || '%'")
    suspend fun searchPlaylists(query: String): List<Playlist>

    @Transaction
    @Query("SELECT * FROM ${Constants.Tables.PLAYLIST_TABLE} NATURAL LEFT JOIN " +
            "(SELECT playlistId, COUNT(*) AS count FROM " +
            "${Constants.Tables.PLAYLIST_SONG_CROSS_REF_TABLE} GROUP BY playlistId)")
    fun getAllPlaylistWithSongCount(): Flow<List<PlaylistWithSongCount>>

}