package com.ravisharma.zen_music.search

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ravisharma.zen_music.data.DataManager
import com.ravisharma.zen_music.data.music.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val context: Application,
    private val manager: DataManager,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _searchType = MutableStateFlow(SearchType.Songs)
    val searchType = _searchType.asStateFlow()

    val searchResult = _query
        .combine(searchType) { query, type ->
            val trimmedQuery = query.trim()
            if (trimmedQuery.isEmpty()) {
                SearchResult()
            } else {
                when (type) {
                    SearchType.Songs -> SearchResult(songs = manager.searchSongs(trimmedQuery))
                    SearchType.Albums -> SearchResult(albums = manager.searchAlbums(trimmedQuery))
                    SearchType.Artists -> SearchResult(artists = manager.searchArtists(trimmedQuery))
                    SearchType.AlbumArtists -> SearchResult(albumArtists = manager.searchAlbumArtists(trimmedQuery))
                    SearchType.Composers -> SearchResult(composers = manager.searchComposers(trimmedQuery))
                    SearchType.Lyricists -> SearchResult(lyricists = manager.searchLyricists(trimmedQuery))
                    SearchType.Genres -> SearchResult(genres = manager.searchGenres(trimmedQuery))
                    SearchType.Playlists -> SearchResult(playlists = manager.searchPlaylists(trimmedQuery))
                }
            }
        }.catch { exception ->
            Timber.e(exception)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchResult()
        )

    fun clearQueryText() {
        _query.update { "" }
    }

    fun updateQuery(query: String) {
        _query.update { query }
    }

    fun updateType(type: SearchType) {
        _searchType.update { type }
    }

    fun setQueue(songs: List<Song>?, startPlayingFromIndex: Int = 0) {
        if (songs == null) return
        manager.setQueue(songs, startPlayingFromIndex)
        Toast.makeText(context, "Playing", Toast.LENGTH_SHORT).show()
    }

}