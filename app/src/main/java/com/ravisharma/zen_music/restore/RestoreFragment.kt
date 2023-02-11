package com.ravisharma.zen_music.restore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.LayoutDirection
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ravisharma.zen_music.data.ZenPreferenceProvider
import com.ravisharma.zen_music.ui.theme.ZenTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RestoreFragment: Fragment() {

    private val viewModel: RestoreViewModel by viewModels()

    private lateinit var navController: NavController

    @Inject
    lateinit var preferenceProvider: ZenPreferenceProvider

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navController = findNavController()
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val theme by preferenceProvider.theme.collectAsState()
                ZenTheme(theme) {
                    val songs by viewModel.blackListedSongs.collectAsState()
                    val selectList = viewModel.restoreList
                    Scaffold(
                        topBar = {
                            RestoreTopBar(
                                onCancelClicked = navController::popBackStack,
                                onConfirmClicked = {
                                    viewModel.restoreSongs()
                                    navController.popBackStack()
                                }
                            )
                        },
                        content = { paddingValues ->
                            val insetsPadding =
                                WindowInsets.systemBars.only(WindowInsetsSides.Horizontal).asPaddingValues()
                            if (selectList.size != songs.size) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(paddingValues),
                                    contentAlignment = Alignment.Center
                                ){
                                    CircularProgressIndicator()
                                }
                            } else {
                                RestoreContent(
                                    songs = songs,
                                    selectList = selectList,
                                    paddingValues = PaddingValues(
                                        top = paddingValues.calculateTopPadding(),
                                        start = insetsPadding.calculateStartPadding(LayoutDirection.Ltr),
                                        end = insetsPadding.calculateEndPadding(LayoutDirection.Ltr),
                                        bottom = insetsPadding.calculateBottomPadding()
                                    ),
                                    onSelectChanged = viewModel::updateRestoreList
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}