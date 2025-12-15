package com.example.calculadoraimc.feature.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.feature.home.model.IMCGraphicStatus
import com.example.calculadoraimc.feature.home.model.statusGraphic
import com.example.calculadoraimc.ui.theme.BlueColor
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

/**
 * Gráfico de acordo com o índice IMC.
 */
@Composable
fun IMCGraphic(
    imcValue: Double
) {
    var percentValue by remember { mutableDoubleStateOf(0.0) }
    percentValue = (if (imcValue >= 40.0) 40.0 else imcValue) / 40.0

    var animationProgress = remember { Animatable(0f) }

    val statusIMCGraphic: IMCGraphicStatus = when {
        imcValue < 18.5f -> statusGraphic["atencao"]!!
        imcValue in 18.5f..24.9f -> statusGraphic["normal"]!!
        imcValue in 25.0f..29.9f -> statusGraphic["atencao"]!!
        imcValue >= 30.0f -> statusGraphic["critico"]!!
        else -> error("IMC invalido")
    }

    LaunchedEffect(imcValue) {
        animationProgress.snapTo(0f) // reseta
        animationProgress.animateTo(
            targetValue = percentValue.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
    ) {
        val size = maxWidth.coerceAtMost(maxHeight) * 0.7f

        IMCGraphicDrawer(
            imcPercent = animationProgress.value,
            modifier = Modifier.size(size),
            statusIMCGraphic
        )

        Column {
            Icon(
                modifier = Modifier.size(52.dp),
                painter = painterResource(statusIMCGraphic.iconID),
                contentDescription = "Icone do gráfico",
                tint = statusIMCGraphic.color
            )
        }

    }
}


/**
 * Drawer do gráfico.
 */
@Composable
fun IMCGraphicDrawer(
    imcPercent: Float,
    modifier: Modifier = Modifier,
    status: IMCGraphicStatus
) {
    Canvas(modifier = modifier) {
        val canvasSize = size.minDimension
        val strokeWidth = 30f

        drawArc(
            color = Color.White.copy(alpha = 0.4f),
            startAngle = 140f,
            sweepAngle = 260f,
            useCenter = false,
            size = Size(canvasSize, canvasSize),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )

        drawArc(
            color = status.color,
            startAngle = 140f,
            sweepAngle = 260f * imcPercent,
            useCenter = false,
            size = Size(canvasSize, canvasSize),
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IMCGraphicPreview() {
    CalculadoraIMCTheme {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(BlueColor)
        ) {
            IMCGraphic(
                imcValue = 17.9
            )
        }
    }
}
