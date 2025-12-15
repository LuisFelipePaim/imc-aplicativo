package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.model.MetricCardData
import com.example.calculadoraimc.ui.models.useIcon
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.example.calculadoraimc.ui.theme.CardGreen

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    metrics: MetricCardData
) {
    // 1. Tratamento dos valores (Altura precisa dividir por 100)
    val valueCard = when (metrics) {
        is MetricCardData.Height -> (metrics.value / 100)
        else -> metrics.value
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = metrics.color // A cor vem definida dentro do objeto metrics
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Linha superior: Título e Ícone
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = metrics.title)

                IconTag(
                    icon = useIcon(metrics.icon),
                    contentDescription = "Icone ${metrics.title}"
                )
            }

            // Linha inferior: Valor e Unidade
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = if (metrics is MetricCardData.Tmb || metrics is MetricCardData.Tdee)
                        String.format("%.0f", valueCard)
                    else if (metrics is MetricCardData.BodyFat)
                        String.format("%.1f", valueCard)
                    else
                        valueCard.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 32.sp
                    )
                )

                Spacer(Modifier.width(2.dp))

                Text(
                    text = metrics.unitMeasure,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = Color(132, 132, 132, 255)
                )
            }
        }
    }
}

@Preview
@Composable
fun MetricCardPreview() {
    CalculadoraIMCTheme {
        MetricCard(
            metrics = MetricCardData.Height(175f)
        )
    }
}