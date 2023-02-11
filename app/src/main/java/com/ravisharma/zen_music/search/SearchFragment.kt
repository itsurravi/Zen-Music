package com.ravisharma.zen_music.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ravisharma.zen_music.collection.CollectionType
import com.ravisharma.zen_music.components.FullScreenSadMessage
import com.ravisharma.zen_music.data.ZenPreferenceProvider
import com.ravisharma.zen_music.data.music.*
import com.ravisharma.zen_music.ui.theme.ZenTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var navController: NavController

    private val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var preferenceProvider: ZenPreferenceProvider

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val themePreference by preferenceProvider.theme.collectAsStateWithLifecycle()
                ZenTheme(themePreference) {
                    val query by viewModel.query.collectAsStateWithLifecycle()
                    val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()
                    val searchType by viewModel.searchType.collectAsStateWithLifecycle()

                    val showGrid by remember {
                        derivedStateOf {
                            ((searchType == SearchType.Songs) || (searchType == SearchType.Albums))
                        }
                    }
                    Scaffold(
                        topBar = {
                            SearchBar(
                                query = query,
                                onQueryChange = viewModel::updateQuery,
                                onBackArrowPressed = navController::popBackStack,
                                currentType = searchType,
                                onSearchTypeSelect = viewModel::updateType,
                                onClearRequest = viewModel::clearQueryText
                            )
                        },
                        content = { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .windowInsetsPadding(
                                        WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                                    )
                            ) {
                                if (searchResult.errorMsg != null) {
                                    FullScreenSadMessage(searchResult.errorMsg)
                                } else {
                                    ResultContent(
                                        searchResult = searchResult,
                                        showGrid = showGrid,
                                        searchType = searchType,
                                        onSongClicked = this@SearchFragment::handleClick,
                                        onAlbumClicked = this@SearchFragment::handleClick,
                                        onArtistClicked = this@SearchFragment::handleClick,
                                        onAlbumArtistClicked = this@SearchFragment::handleClick,
                                        onComposerClicked = this@SearchFragment::handleClick,
                                        onLyricistClicked = this@SearchFragment::handleClick,
                                        onGenreClicked = this@SearchFragment::handleClick,
                                        onPlaylistClicked = this@SearchFragment::handleClick,
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun handleClick(song: Song){
        viewModel.setQueue(listOf(song))
    }

    private fun handleClick(album: Album){
        navController.navigate(
            SearchFragmentDirections
                .actionSearchFragmentToCollectionFragment(CollectionType(CollectionType.AlbumType,album.name))
        )
    }

    private fun handleClick(artist: Artist){
        navController.navigate(
            SearchFragmentDirections
                .actionSearchFragmentToCollectionFragment(CollectionType(CollectionType.ArtistType,artist.name))
        )
    }

    private fun handleClick(albumArtist: AlbumArtist){
        navController.navigate(
            SearchFragmentDirections
                .actionSearchFragmentToCollectionFragment(CollectionType(CollectionType.AlbumArtistType,albumArtist.name))
        )
    }

    private fun handleClick(composer: Composer){
        navController.navigate(
            SearchFragmentDirections
                .actionSearchFragmentToCollectionFragment(CollectionType(CollectionType.ComposerType,composer.name))
        )
    }

    private fun handleClick(lyricist: Lyricist){
        navController.navigate(
            SearchFragmentDirections.actionSearchFragmentToCollectionFragment(
                CollectionType(CollectionType.LyricistType,lyricist.name)
            )
        )
    }

    private fun handleClick(genre: Genre){
        navController.navigate(
            SearchFragmentDirections.actionSearchFragmentToCollectionFragment(CollectionType(CollectionType.GenreType,genre.genre))
        )
    }

    private fun handleClick(playlist: Playlist){
        navController.navigate(
            SearchFragmentDirections
                .actionSearchFragmentToCollectionFragment(CollectionType(CollectionType.PlaylistType,playlist.playlistId.toString()))
        )
    }
}