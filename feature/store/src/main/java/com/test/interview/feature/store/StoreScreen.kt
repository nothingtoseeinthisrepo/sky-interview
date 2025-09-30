package com.test.interview.feature.store

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun StoreScreen(
    viewModel: StoreViewModel = hiltViewModel()
) = Box(
    modifier = Modifier
        .fillMaxSize(),
    contentAlignment = Alignment.Center,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Text(
        text = state,
        color = Color.Black,
        textAlign = TextAlign.Center,
        fontSize = 24.sp
    )
}