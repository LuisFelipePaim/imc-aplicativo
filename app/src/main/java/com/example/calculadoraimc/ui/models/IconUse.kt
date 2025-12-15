package com.example.calculadoraimc.ui.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource

/**
 * Usos de icones com base no tipo
 * @property Vector - Icones de bibliotecas compativeis com `ImageVector`
 * @property Painter - Icones e quaisquer objetos drawables compativeis com `Painter`
 */

sealed class IconUse {
    data class Vector(val icon: ImageVector) : IconUse()
    data class Painter(@DrawableRes val icon: Int) : IconUse()
}

@Composable
fun useIcon(iconUse: IconUse): Painter = when (iconUse) {
    is IconUse.Painter -> painterResource(id = iconUse.icon)
    is IconUse.Vector -> rememberVectorPainter(image = iconUse.icon)
}