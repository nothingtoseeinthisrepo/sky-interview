package com.test.interview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.test.interview.feature.detail.PokemonDetailScreen
import com.test.interview.feature.list.PokemonListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                var showDetail by remember { mutableStateOf(false) }
                if (showDetail) {
                    PokemonDetailScreen()
                } else {
                    PokemonListScreen { showDetail = true }
                }
            }
        }
    }
}