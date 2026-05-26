package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.BrawlersScreen
import com.example.ui.screens.DocsScreen
import com.example.ui.screens.MainMenuScreen
import com.example.ui.screens.ShopScreen
import com.example.ui.screens.SimulationScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: GameViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsState()
                val activeGame by viewModel.gameActive.collectAsState()

                Box(modifier = Modifier.fillMaxSize()) {
                    if (activeGame) {
                        // Directly show the interactive playable combat simulation
                        SimulationScreen(viewModel = viewModel)
                    } else {
                        // Dynamic crossfade screens navigation
                        Crossfade(targetState = currentScreen, label = "ScreenTransition") { screen ->
                            when (screen) {
                                is GameViewModel.Screen.MainMenu -> MainMenuScreen(viewModel = viewModel)
                                is GameViewModel.Screen.Wiki -> BrawlersScreen(viewModel = viewModel)
                                is GameViewModel.Screen.Shop -> ShopScreen(viewModel = viewModel)
                                is GameViewModel.Screen.UnityGuide -> DocsScreen(viewModel = viewModel)
                                else -> MainMenuScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
