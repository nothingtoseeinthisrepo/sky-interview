package com.test.interview.feature.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.test.interview.feature.pokemon.model.Pokemon
import com.test.interview.feature.pokemon.ui.backgroundColor
import com.test.interview.feature.pokemon.ui.highlightColor

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val pokemon = state ?: return
    val pokemonType = pokemon.types.getOrElse(0) { Pokemon.Type.Unknown }
    val backgroundColor = animateColorAsState(
        targetValue = pokemonType.backgroundColor,
        label = "BackgroundColor",
        animationSpec = COLOR_ANIMATION_SPEC,
    )
    val highlightColor = animateColorAsState(
        targetValue = pokemonType.highlightColor,
        label = "HighlightColor",
        animationSpec = COLOR_ANIMATION_SPEC,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(backgroundColor.value),
    ) {
        Header(
            number = pokemon.number,
            title = pokemon.name,
            height = pokemon.height,
            weight = pokemon.weight,
            onBackPress = {
                // TODO: Close screen
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        ArtworkWithType(
            title = pokemon.name,
            imageUrl = pokemon.imageUrl,
            types = pokemon.types,
        )
        pokemon.stats?.let { stats ->
            Spacer(modifier = Modifier.height(16.dp))
            StatsSection(stats = stats, color = highlightColor.value)
        }
    }
}

@Composable
internal fun BackButton(
    onBackPress: () -> Unit,
) {
    IconButton(
        onClick = onBackPress,
        modifier = Modifier
            .padding(start = 16.dp, top = 28.dp, end = 12.dp)
            .size(34.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
        )
    }
}

@Composable
internal fun Header(
    number: Int?,
    title: String,
    height: Int?,
    weight: Float?,
    onBackPress: () -> Unit,
) = Row {
    BackButton(onBackPress = onBackPress)

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 4.dp, top = 16.dp, end = 4.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(start = 16.dp, end = 16.dp)
            .height(65.dp)
            .weight(1f),
    ) {
        Text(
            text = title,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .wrapContentWidth(),
        )
        if (height != null && weight != null) {
            Text(
                text = "$weight kg | $height cm",
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .wrapContentWidth(),
            )
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 4.dp, top = 16.dp, end = 16.dp)
            .height(65.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(start = 12.dp, end = 12.dp)
            .wrapContentWidth(),
    ) {
        Text(
            text = "#${String.format(null, "%04d", number)}",
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .wrapContentWidth(),
        )
    }
}

@Composable
internal fun ArtworkWithType(
    title: String?,
    imageUrl: String?,
    types: List<Pokemon.Type>,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        .background(Color.White, RoundedCornerShape(20.dp))
        .fillMaxWidth(),
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = title,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .aspectRatio(1f),
    )
    Spacer(modifier = Modifier.height(8.dp))
    TypesSection(types)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
internal fun TypesSection(
    types: List<Pokemon.Type>,
) = FlowRow(
    horizontalArrangement = Arrangement.Center,
    maxItemsInEachRow = 3,
) {
    for (type in types) {
        val color = type.backgroundColor
        Text(
            text = type.name,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            modifier = Modifier
                .padding(4.dp, 0.dp, 4.dp, 0.dp)
                .background(color, RoundedCornerShape(100))
                .padding(12.dp, 2.dp, 12.dp, 2.dp)
                .wrapContentWidth(),
        )
    }
}

@Composable
internal fun StatsSection(
    stats: Pokemon.Stats,
    color: Color,
) = Box {
    Text(
        text = "Stats",
        overflow = TextOverflow.Ellipsis,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .background(color, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(start = 24.dp, end = 24.dp, top = 4.dp, bottom = 32.dp)
            .fillMaxWidth(),
    )
    FlowRow(
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 32.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Stat(statName = "HP", statValue = stats.hpStat, color = Color(0xFFFC797A))
        Stat(statName = "Atk", statValue = stats.attackStat, color = Color(0xFFF1B285))
        Stat(statName = "Def", statValue = stats.defenseStat, color = Color(0xFFEED881))
        Stat(statName = "Sp. Atk", statValue = stats.specialAttackStat, color = Color(0xFF9BB2E9))
        Stat(statName = "Sp. Def", statValue = stats.specialDefenseStat, color = Color(0xFFABD597))
        Stat(statName = "Speed", statValue = stats.speedStat, color = Color(0xFFE796AF))
    }
}

@Composable
internal fun Stat(
    statName: String,
    statValue: Int?,
    color: Color,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
        .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 12.dp)
        .background(color, RoundedCornerShape(20.dp))
        .width(75.dp)
        .height(55.dp),
) {
    Text(
        text = statValue?.toString() ?: "???",
        overflow = TextOverflow.Ellipsis,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier
            .wrapContentWidth(),
    )
    Text(
        text = statName,
        overflow = TextOverflow.Ellipsis,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .wrapContentWidth(),
    )
}

private val COLOR_ANIMATION_SPEC = tween<Color>(400, 0, LinearEasing)
