package com.ravisharma.zen_music.data.daos

import androidx.room.*
import com.ravisharma.zen_music.Constants
import com.ravisharma.zen_music.data.music.Lyricist
import com.ravisharma.zen_music.data.music.LyricistWithSongs
import kotlinx.coroutines.flow.Flow

@Dao
interface LyricistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllLyricists(data: List<Lyricist>)

    @Query("SELECT * FROM ${Constants.Tables.LYRICIST_TABLE} WHERE name LIKE '%' || :query || '%'")
    suspend fun searchLyricists(query: String): List<Lyricist>

    @Transaction
    @Query("SELECT * FROM ${Constants.Tables.LYRICIST_TABLE} WHERE name = :name")
    fun getLyricistWithSongs(name: String): Flow<LyricistWithSongs?>

}