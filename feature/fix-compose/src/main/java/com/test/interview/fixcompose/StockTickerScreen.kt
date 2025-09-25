@file:OptIn(ExperimentalMaterial3Api::class)

package com.test.interview.fixcompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max
import kotlin.math.min

@Composable
fun StockTickerScreen(
    viewModel: StockTickerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val stocks by viewModel.stocks.collectAsState()

    val mostVolatile by remember(stocks) {
        mutableStateOf(
            stocks.maxByOrNull { stock ->
                stock.history.takeLast(20).let { history ->
                    (history.maxOrNull() ?: 0.0) - (history.minOrNull() ?: 0.0)
                }
            }
        )
    }

    val now = Date()
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val tickString = timeFormat.format(now)

    val screenLevelSparkline = generateSparkline(stocks)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Live Ticker • $tickString")
                        mostVolatile?.let {
                            Text(
                                "Most Volatile: ${it.symbol}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFF101820)),
            ) {
                Text("Market Pulse: ", color = Color.White)
                Sparkline(screenLevelSparkline.takeLast(15))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(stocks) { stock ->
                    val rowSpark = perRowSparkline(stock)

                    StockRow(
                        stock = stock,
                        sparkline = rowSpark,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StockRow(
    stock: Stock,
    sparkline: List<Float>,
    modifier: Modifier = Modifier
) {
    val fmt = remember { DecimalFormat("#,##0.00") }
    val priceText = "${stock.symbol} — ${fmt.format(stock.price)}"

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text(priceText, style = MaterialTheme.typography.titleMedium)
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Sparkline(sparkline.takeLast(15))
            }
        }
        ChangeBadge(stock)
    }
}

@Composable
private fun ChangeBadge(stock: Stock) {
    val change = stock.changePercent
    val color = if (change > 0) Color(0xFF37B24D) else Color(0xFFF03E3E)
    AssistChip(
        onClick = {},
        label = { Text(String.format(Locale.getDefault(), "%.2f%%", change), color = color) }
    )
}

@Composable
fun Sparkline(
    points: List<Float>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        if (points.size < 2) return@Canvas

        val w = size.width
        val h = size.height

        var minValue = Float.POSITIVE_INFINITY
        var maxValue = Float.NEGATIVE_INFINITY
        for (value in points) {
            minValue = min(minValue, value)
            maxValue = max(maxValue, value)
        }
        val span = if (maxValue - minValue == 0f) 1f else (maxValue - minValue)
        val stepX = if (points.size <= 1) w else w / (points.size - 1).toFloat()

        val stroke = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)

        for (i in 1 until points.size) {
            val prev = points[i - 1]
            val curr = points[i]

            val x0 = (i - 1) * stepX
            val x1 = i * stepX
            val y0 = h - ((prev - minValue) / span) * h
            val y1 = h - ((curr - minValue) / span) * h

            val color = when {
                curr > prev -> Color(0xFF37B24D)
                curr < prev -> Color(0xFFF03E3E)
                else -> Color(0xFFADB5BD)
            }

            drawLine(
                color = color,
                start = Offset(x0, y0),
                end = Offset(x1, y1),
                strokeWidth = stroke.width,
                cap = stroke.cap
            )

            drawCircle(
                color = color.copy(alpha = 0.55f),
                radius = 2.5.dp.toPx(),
                center = Offset(x1, y1)
            )
        }
    }
}


private fun generateSparkline(stocks: List<Stock>): List<Float> {
    val maxLength = stocks.maxOfOrNull { it.history.size } ?: return emptyList()

    val last = DoubleArray(stocks.size)

    val averages = MutableList(maxLength) { 0.0f }
    for (i in 0 until maxLength) {
        var sum = 0.0
        for ((idx, item) in stocks.withIndex()) {
            val v = item.history.getOrNull(i) ?: last[idx] // carry forward
            last[idx] = v
            sum += v
        }
        averages[i] = (sum / stocks.size).toFloat()
    }

    return averages
}

private fun perRowSparkline(stock: Stock): List<Float> {
    val h = stock.history
    if (h.isEmpty()) return listOf(0f)
    return h.map { it.toFloat() }
}