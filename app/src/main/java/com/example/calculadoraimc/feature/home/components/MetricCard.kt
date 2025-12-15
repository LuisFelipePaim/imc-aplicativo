package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.model.MetricCardData
import com.example.calculadoraimc.ui.models.useIcon

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    metrics: MetricCardData
) {
    // CORREÇÃO AQUI: Adicionamos os casos Tmb e Tdee
    val valueCard = when (metrics) {
        is MetricCardData.Height -> (metrics.value / 100)
        is MetricCardData.Weight -> metrics.value
        is MetricCardData.Tmb -> metrics.value   // <--- Linha Nova
        is MetricCardData.Tdee -> metrics.value  // <--- Linha Nova
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = metrics.color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = metrics.title)
                IconTag(icon = useIcon(metrics.icon), contentDescription = metrics.title)
            }

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    // Lógica para não mostrar decimais na TMB/TDEE (são números grandes)
                    text = if(metrics is MetricCardData.Tmb || metrics is MetricCardData.Tdee)
                        String.format("%.0f", valueCard)
                    else valueCard.toString(),
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