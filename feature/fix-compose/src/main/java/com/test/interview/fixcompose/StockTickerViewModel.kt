package com.test.interview.fixcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

data class Stock(
    val symbol: String,
    var price: Double,
    val history: MutableList<Double> = mutableListOf()
) {
    val changePercent: Double
        get() = if (history.size < 2) 0.0
        else (price - history[history.lastIndex - 1]) / history[history.lastIndex - 1] * 100
}

class StockTickerViewModel : ViewModel() {
    private val _stocks = MutableStateFlow(
        listOf(
            Stock("AAPL", 190.12),
            Stock("GOOG", 142.80),
            Stock("AMZN", 177.44),
            Stock("MSFT", 412.32),
            Stock("TSLA", 256.09),
            Stock("NVDA", 118.22),
            Stock("META", 497.31),
            Stock("NFLX", 382.50),
            Stock("ORCL", 118.92),
            Stock("IBM", 148.33),
            Stock("INTC", 44.28),
            Stock("AMD", 96.77),
        )
    )
    val stocks: StateFlow<List<Stock>> = _stocks

    private var tickerJob: Job? = null

    init {
        tickerJob = viewModelScope.launch {
            while (isActive) {
                val updated = _stocks.value.map { it }.toMutableList()
                repeat(2) {
                    val idx = Random.nextInt(updated.size)
                    val old = updated[idx]

                    val delta = (Random.nextDouble() - 0.5) * 2.0
                    val newPrice = (old.price + delta).coerceAtLeast(0.01)

                    val newHistory = (old.history + newPrice).let { hist ->
                        if (hist.size > 120) hist.drop(1) else hist
                    }

                    updated[idx] = Stock(
                        symbol = old.symbol,
                        price = newPrice,
                        history = newHistory.toMutableList()
                    )
                }

                _stocks.value = updated.toMutableList()

                // only emits a random stock update every 3 seconds
                delay(3.seconds)
            }
        }
    }

    override fun onCleared() {
        tickerJob?.cancel()
        super.onCleared()
    }
}
