package com.ravisharma.zen_music.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.LayoutDirection
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ravisharma.zen_music.components.TopBarWithBackArrow
import com.ravisharma.zen_music.data.ZenPreferenceProvider
import com.ravisharma.zen_music.ui.theme.ZenTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.ravisharma.zen_music.R

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel :SettingsViewModel by viewModels()

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
                val themePreference by preferenceProvider.theme.collectAsState()
                val scanStatus by viewModel.scanStatus.collectAsState()
                ZenTheme(
                    themePreference = themePreference
                ) {
                    Scaffold(
                        topBar = {
                            TopBarWithBackArrow(
                                onBackArrowPressed = navController::popBackStack,
                                title = "Settings",
                                actions = { }
                            )
                        },
                        content = { paddingValues ->
                            val insetsPadding =
                                WindowInsets.systemBars.only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal).asPaddingValues()
                            SettingsList(
                                paddingValues = PaddingValues(
                                    top = paddingValues.calculateTopPadding(),
                                    start = insetsPadding.calculateStartPadding(LayoutDirection.Ltr),
                                    end = insetsPadding.calculateEndPadding(LayoutDirection.Ltr),
                                    bottom = insetsPadding.calculateBottomPadding()
                                ),
                                themePreference = themePreference,
                                onThemePreferenceChanged = preferenceProvider::updateTheme,
                                scanStatus = scanStatus,
                                onScanClicked = viewModel::scanForMusic,
                                onRestoreClicked = {
                                    navController.navigate(
                                        R.id.action_settingsFragment_to_restoreFragment
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}