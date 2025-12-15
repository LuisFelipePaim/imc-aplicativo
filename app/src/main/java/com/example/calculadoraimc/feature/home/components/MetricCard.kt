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
import com.example.calculadoraimc.ui.theme.GreyColor

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    metrics: MetricCardData
) {

    // Calculo do valor a ser mostrado
    val valueCard = when (metrics) {
        is MetricCardData.Height -> (metrics.value / 100)
        is MetricCardData.Weight -> metrics.value
    }


    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = metrics.color
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Linha superior
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = metrics.title
                )

                IconTag(
                    icon = useIcon(metrics.icon),
                    contentDescription = "Icone do cara para peso."
                )
            }

            // Valores
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = valueCard.toString(),
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

@Preview(showBackground = true)
@Composable
private fun MetricCardPreview() {
    CalculadoraIMCTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                Modifier.weight(1f),
                metrics = MetricCardData.Height(3f),
            )
            MetricCard(
                Modifier.weight(1f),
                metrics = MetricCardData.Weight(5f),
            )
        }
    }
}