package com.test.interview.feature.list

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import coil3.load
import com.test.interview.feature.list.databinding.ListItemPokemonBinding
import com.test.interview.feature.pokemon.model.Pokemon
import com.test.interview.feature.pokemon.ui.backgroundColor
import com.test.interview.feature.pokemon.ui.highlightColor

@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    onClick: (Pokemon) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context -> PokemonListItemView(context) },
        update = { it.setPokemon(pokemon, onClick) }
    )
}

class PokemonListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ListItemPokemonBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = HORIZONTAL
        val padding = (8 * resources.displayMetrics.density).toInt()
        setPadding(padding, padding, padding, padding)

        isClickable = true
        isFocusable = true
    }

    fun setPokemon(pokemon: Pokemon, onClick: (Pokemon) -> Unit) {
        binding.pokemonImage.load(pokemon.imageUrl)
        binding.pokemonNameText.text = pokemon.name

        pokemon.types.forEachIndexed { index, type ->
            val layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )

            if (index > 0) {
                layoutParams.marginStart = (8 * resources.displayMetrics.density).toInt()
            }

            binding.pokemonTypeContainer.addView(
                PokemonTypeView(context).also { it.setType(type) },
                layoutParams
            )
        }

        val attributeValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, attributeValue, true)
        val ripple = context.getDrawable(attributeValue.resourceId) as? RippleDrawable
        val rippleColor = pokemon.types.firstOrNull()?.highlightColor?.copy(alpha = .5f)
        rippleColor?.let {
            ripple?.setColor(ColorStateList.valueOf(it.toArgb()))
        }
        foreground = ripple

        binding.root.setOnClickListener { onClick(pokemon) }
    }
}

class PokemonTypeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : TextView(context, attrs, defStyleAttr) {

    init {
        val horizontalPadding = (8 * resources.displayMetrics.density).toInt()
        val verticalPadding = (4 * resources.displayMetrics.density).toInt()
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        gravity = Gravity.CENTER
    }

    fun setType(type: Pokemon.Type) {
        text = type.name
        background = GradientDrawable().apply {
            cornerRadius = Float.MAX_VALUE
            setColor(type.backgroundColor.toArgb())
        }
    }
}
