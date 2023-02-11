package com.ravisharma.zen_music.search

enum class SearchType(val text: String) {
    Songs("Songs"),
    Albums("Albums"),
    Artists("Artists"),
    AlbumArtists("Album artists"),
    Lyricists("Lyricists"),
    Composers("Composers"),
    Genres("Genres"),
    Playlists("Playlists")
}