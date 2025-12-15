package com.example.calculadoraimc.feature.home.model

import androidx.compose.ui.graphics.Color
import com.example.calculadoraimc.R
import com.example.calculadoraimc.ui.theme.GreenLevel
import com.example.calculadoraimc.ui.theme.OrangeLevel
import com.example.calculadoraimc.ui.theme.RedLevel

data class IMCGraphicStatus(val color: Color, val iconID: Int)

val statusGraphic = mapOf(
    "critico" to IMCGraphicStatus(
        color = RedLevel,
        iconID = R.drawable.sentiment_sad_24px
    ),
    "atencao" to IMCGraphicStatus(
        color = OrangeLevel,
        iconID = R.drawable.sentiment_neutral_24px
    ),
    "normal" to IMCGraphicStatus(
        color = GreenLevel,
        iconID = R.drawable.sentiment_satisfied_24px
    )
)