package com.ravisharma.zen_music.data.daos

import androidx.room.*
import com.ravisharma.zen_music.Constants
import com.ravisharma.zen_music.data.music.Album
import com.ravisharma.zen_music.data.music.AlbumWithSongs
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAlbums(data: List<Album>)

    @Query("SELECT * FROM ${Constants.Tables.ALBUM_TABLE} ORDER BY name ASC")
    fun getAllAlbums(): Flow<List<Album>>

    @Transaction
    @Query("SELECT * FROM ${Constants.Tables.ALBUM_TABLE} WHERE name = :albumName")
    fun getAlbumWithSongsByName(albumName: String): Flow<AlbumWithSongs?>

    @Query("DELETE FROM ${Constants.Tables.ALBUM_TABLE}")
    suspend fun deleteAllAlbums()

    @Query("SELECT * FROM ${Constants.Tables.ALBUM_TABLE} WHERE name LIKE '%' || :query || '%'")
    suspend fun searchAlbums(query: String): List<Album>

}