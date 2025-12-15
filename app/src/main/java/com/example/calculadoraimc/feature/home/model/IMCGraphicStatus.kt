package com.example.calculadoraimc.feature.home.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.rounded.SentimentSatisfied
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.calculadoraimc.ui.theme.HealthTertiary

data class IMCGraphicStatus(
    val label: String,
    val color: Color,
    val icon: ImageVector // Mudamos para vetor nativo
)

val statusGraphic = mapOf(
    "normal" to IMCGraphicStatus(
        label = "Normal",
        color = Color(0xFFCDDC39),
        icon = Icons.Rounded.SentimentSatisfied
    ),
    "atencao" to IMCGraphicStatus(
        label = "Atenção",
        color = HealthTertiary,
        icon = Icons.Rounded.SentimentNeutral
    ),
    "critico" to IMCGraphicStatus(
        label = "Crítico",
        color = Color(0xFFFF5252),
        icon = Icons.Rounded.Warning
    )
)