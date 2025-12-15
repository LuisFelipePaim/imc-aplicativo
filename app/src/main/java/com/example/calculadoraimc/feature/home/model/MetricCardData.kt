package com.example.calculadoraimc.feature.home.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Height
import androidx.compose.ui.graphics.Color
import com.example.calculadoraimc.R
import com.example.calculadoraimc.ui.models.IconUse
import com.example.calculadoraimc.ui.theme.GreenColor
import com.example.calculadoraimc.ui.theme.YellowColor

/**
 * Tipos de valores aceitaveis do Card
 * @property Height Altura
 * @property Weight Peso
 */
sealed class MetricCardData(
    val title: String,
    val unitMeasure: String,
    val icon: IconUse,
    open val value: Float,
    val color: Color
) {
    data class Height(override val value: Float) : MetricCardData(
        title = "Altura",
        unitMeasure = "m",
        icon = IconUse.Vector(Icons.Rounded.Height),
        value = value,
        color = GreenColor
    )

    data class Weight(override val value: Float) : MetricCardData(
        title = "Peso",
        unitMeasure = "kg",
        icon = IconUse.Painter(R.drawable.weight_24px),
        value = value,
        color = YellowColor
    )
}