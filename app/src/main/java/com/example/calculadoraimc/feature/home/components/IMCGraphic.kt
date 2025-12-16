package com.example.calculadoraimc.feature.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.feature.home.model.IMCGraphicStatus
import com.example.calculadoraimc.feature.home.model.statusGraphic
import com.example.calculadoraimc.ui.theme.HealthPrimary
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@Composable
fun IMCGraphic(imcValue: Double) {
    val percentValue = (if (imcValue >= 40.0) 40.0 else imcValue) / 40.0
    val animationProgress = remember { Animatable(0f) }

    val statusIMCGraphic: IMCGraphicStatus = when {
        imcValue < 18.5f -> statusGraphic["atencao"]!!
        imcValue in 18.5f..24.9f -> statusGraphic["normal"]!!
        imcValue in 25.0f..29.9f -> statusGraphic["atencao"]!!
        imcValue >= 30.0f -> statusGraphic["critico"]!!
        else -> statusGraphic["atencao"]!!
    }

    LaunchedEffect(imcValue) {
        animationProgress.snapTo(0f)
        animationProgress.animateTo(
            targetValue = percentValue.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(200.dp)
            .width(60.dp)
    ) {
        IMCGraphicDrawer(
            imcPercent = animationProgress.value,
            modifier = Modifier.fillMaxSize(),
            status = statusIMCGraphic
        )
    }
}

@Composable
fun IMCGraphicDrawer(imcPercent: Float, modifier: Modifier = Modifier, status: IMCGraphicStatus) {
    Canvas(modifier = modifier) {
        val barWidth = size.width
        val barHeight = size.height
        val cornerRadius = CornerRadius(20f, 20f) // Arredondamento das bordas

        // 1. Desenha o Fundo (Trilho vazio)
        drawRoundRect(
            color = Color.White.copy(alpha = 0.3f),
            size = size,
            cornerRadius = cornerRadius
        )

        // 2. Calcula a altura do preenchimento baseado na porcentagem
        val fillHeight = barHeight * imcPercent

        // O Canvas desenha de cima para baixo (0,0 é topo-esquerda).
        // Para a barra "subir", o topo do retângulo deve ser: AlturaTotal - AlturaPreenchimento
        val topOffset = barHeight - fillHeight

        drawRoundRect(
            color = status.color,
            topLeft = Offset(0f, topOffset),
            size = Size(barWidth, fillHeight),
            cornerRadius = cornerRadius
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
                .background(HealthPrimary)
                .padding(20.dp)
        ) {
            IMCGraphic(imcValue = 22.0)
        }
    }
}