package com.example.calculadoraimc.feature.home.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt // Raio para energia
import androidx.compose.material.icons.rounded.Height
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.ui.graphics.Color
import com.example.calculadoraimc.R
import com.example.calculadoraimc.ui.models.IconUse
import com.example.calculadoraimc.ui.theme.GreenColor
import com.example.calculadoraimc.ui.theme.YellowColor

sealed class MetricCardData(
    val title: String,
    val unitMeasure: String,
    val icon: IconUse,
    open val value: Float,
    val color: Color
) {
    data class Height(override val value: Float) : MetricCardData(
        title = "Altura", unitMeasure = "m", icon = IconUse.Vector(Icons.Rounded.Height), value = value, color = GreenColor
    )
    data class Weight(override val value: Float) : MetricCardData(
        title = "Peso", unitMeasure = "kg", icon = IconUse.Painter(R.drawable.weight_24px), value = value, color = YellowColor
    )
    data class Tmb(override val value: Float) : MetricCardData(
        title = "TMB (Basal)", unitMeasure = "kcal", icon = IconUse.Vector(Icons.Rounded.LocalFireDepartment), value = value, color = Color(255, 152, 0, 255)
    )

    // --- NOVO CARD ---
    data class Tdee(override val value: Float) : MetricCardData(
        title = "Necessidade",
        unitMeasure = "kcal/dia",
        icon = IconUse.Vector(Icons.Rounded.Bolt),
        value = value,
        color = Color(233, 30, 99, 255) // Rosa
    )
}