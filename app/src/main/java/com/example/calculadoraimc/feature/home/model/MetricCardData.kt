package com.example.calculadoraimc.feature.home.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Height
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Opacity
import androidx.compose.ui.graphics.Color
import com.example.calculadoraimc.R
import com.example.calculadoraimc.ui.models.IconUse
import com.example.calculadoraimc.ui.theme.*

sealed class MetricCardData(
    val title: String,
    val unitMeasure: String,
    val icon: IconUse,
    open val value: Float,
    val color: Color
) {
    data class Height(override val value: Float) : MetricCardData(
        "Altura", "m", IconUse.Vector(Icons.Rounded.Height), value, CardGreen
    )
    data class Weight(override val value: Float) : MetricCardData(
        "Peso", "kg", IconUse.Painter(R.drawable.weight_24px), value, CardYellow
    )
    data class Tmb(override val value: Float) : MetricCardData(
        "TMB", "kcal", IconUse.Vector(Icons.Rounded.LocalFireDepartment), value, CardOrange
    )
    data class Tdee(override val value: Float) : MetricCardData(
        "Necessidade", "kcal/dia", IconUse.Vector(Icons.Rounded.Bolt), value, CardPink
    )
    data class BodyFat(override val value: Float) : MetricCardData(
        "Gordura", "%", IconUse.Vector(Icons.Rounded.Opacity), value, CardPurple
    )
}