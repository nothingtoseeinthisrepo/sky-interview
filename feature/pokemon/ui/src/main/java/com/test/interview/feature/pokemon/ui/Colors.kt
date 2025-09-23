package com.test.interview.feature.pokemon.ui

import androidx.compose.ui.graphics.Color
import com.test.interview.feature.pokemon.model.Pokemon

val Pokemon.Type.backgroundColor: Color
    get() = when (this) {
        Pokemon.Type.Normal -> Color(0xFFA8A877)
        Pokemon.Type.Fire -> Color(0xFFC78D6E)
        Pokemon.Type.Fighting -> Color(0xFFA55C58)
        Pokemon.Type.Water -> Color(0xFF7A8EC0)
        Pokemon.Type.Flying -> Color(0xFF8879B4)
        Pokemon.Type.Grass -> Color(0xFF79996A)
        Pokemon.Type.Poison -> Color(0xFF976597)
        Pokemon.Type.Electric -> Color(0xFFC4B16E)
        Pokemon.Type.Ground -> Color(0xFFA09065)
        Pokemon.Type.Psychic -> Color(0xFFA2697A)
        Pokemon.Type.Rock -> Color(0xFFA79C6C)
        Pokemon.Type.Ice -> Color(0xFF7EACAC)
        Pokemon.Type.Bug -> Color(0xFF989E68)
        Pokemon.Type.Dragon -> Color(0xFF8271AD)
        Pokemon.Type.Ghost -> Color(0xFF766592)
        Pokemon.Type.Dark -> Color(0xFF726156)
        Pokemon.Type.Steel -> Color(0xFF9797AA)
        Pokemon.Type.Fairy -> Color(0xFFEEA5B5)
        Pokemon.Type.Shadow -> Color(0xFF262626)
        Pokemon.Type.Unknown -> Color(0xFFE0E0E0)
    }

val Pokemon.Type.highlightColor: Color
    get() = when (this) {
        Pokemon.Type.Normal -> Color(0xFFDADA9E)
        Pokemon.Type.Fire -> Color(0xFFEEA672)
        Pokemon.Type.Fighting -> Color(0xFFE2716B)
        Pokemon.Type.Water -> Color(0xFF90ACEE)
        Pokemon.Type.Flying -> Color(0xFFAD96F0)
        Pokemon.Type.Grass -> Color(0xFFA3D889)
        Pokemon.Type.Poison -> Color(0xFFE292E2)
        Pokemon.Type.Electric -> Color(0xFFEBD88C)
        Pokemon.Type.Ground -> Color(0xFFF3DA97)
        Pokemon.Type.Psychic -> Color(0xFFFC95B4)
        Pokemon.Type.Rock -> Color(0xFFE7D588)
        Pokemon.Type.Ice -> Color(0xFFBEF0F0)
        Pokemon.Type.Bug -> Color(0xFFE1E99D)
        Pokemon.Type.Dragon -> Color(0xFFB096EE)
        Pokemon.Type.Ghost -> Color(0xFFB79AE7)
        Pokemon.Type.Dark -> Color(0xFFB99984)
        Pokemon.Type.Steel -> Color(0xFFC9C9DA)
        Pokemon.Type.Fairy -> Color(0xFFF8B9C7)
        Pokemon.Type.Shadow -> Color(0xFF464646)
        Pokemon.Type.Unknown -> Color(0xFFB4B4B4)
    }
