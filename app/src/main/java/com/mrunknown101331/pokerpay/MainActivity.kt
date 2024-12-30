package com.mrunknown101331.pokerpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrunknown101331.pokerpay.ui.theme.PokerPayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokerPayTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "Entry")
                    {
                        composable(route = "Entry")
                        {
                            EntryLayout(Modifier.padding(innerPadding), navController)
                        }
                        composable(route = "Final/{GameID}/{PlayerID}")
                        { Lists ->
                            val gameId = Lists.arguments?.getString("GameID")
                            val playerId = Lists.arguments?.getString("PlayerID")
                            if (gameId != null && playerId != null) {
                                FinalLayout(
                                    Modifier.padding(innerPadding),
                                    navController,
                                    gameId,
                                    playerId
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
