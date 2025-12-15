package com.example.calculadoraimc.feature.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.database.IMCResultEntity
// IMPORTS DO NOVO TEMA
import com.example.calculadoraimc.ui.theme.HealthPrimary
import com.example.calculadoraimc.ui.theme.HealthSecondary

@Composable
fun HistoryScreen(
    historyList: List<IMCResultEntity>,
    onDelete: (IMCResultEntity) -> Unit
) {
    var selectedItem by remember { mutableStateOf<IMCResultEntity?>(null) }

    if (historyList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Nenhum histórico ainda.", color = Color.Gray)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // GRÁFICO NO TOPO
            item {
                HistoryChart(historyData = historyList)
            }

            // LISTA DE ITENS
            items(historyList) { item ->
                HistoryItemCard(item, onClick = { selectedItem = item })
            }
        }
    }

    // Modal (Dialog) de Detalhes
    selectedItem?.let { item ->
        DetailDialog(
            item = item,
            onClose = { selectedItem = null },
            onDelete = {
                onDelete(item)
                selectedItem = null
            }
        )
    }
}

@Composable
fun HistoryItemCard(item: IMCResultEntity, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = item.getFormattedDate(), fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "IMC: ${String.format("%.2f", item.imc)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = HealthPrimary // Usando a cor do tema
                )
                Text(text = item.classification, fontSize = 14.sp)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = "Ver detalhes",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun DetailDialog(item: IMCResultEntity, onClose: () -> Unit, onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(text = "Detalhes da Medição", color = HealthSecondary) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DetailRow("Data", item.getFormattedDate())
                HorizontalDivider()
                DetailRow("Peso", "${item.weight} kg")
                DetailRow("Altura", "${item.height} cm")
                DetailRow("IMC", String.format("%.2f", item.imc))
                DetailRow("Situação", item.classification)

                if (item.idealWeightMin > 0) {
                    HorizontalDivider()
                    Text("Estimativas:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = HealthPrimary)
                    DetailRow("Peso Ideal Min", "${String.format("%.1f", item.idealWeightMin)} kg")
                    DetailRow("Peso Ideal Max", "${String.format("%.1f", item.idealWeightMax)} kg")
                }

                if (item.tmb > 0) {
                    HorizontalDivider()
                    Text("Energia & Corpo:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = HealthPrimary)
                    DetailRow("TMB (Basal)", "${String.format("%.0f", item.tmb)} kcal")

                    if (item.tdee > 0) {
                        DetailRow("Nec. Diária", "${String.format("%.0f", item.tdee)} kcal")
                    }
                    if (item.bodyFat > 0) {
                        DetailRow("Gordura", "${String.format("%.1f", item.bodyFat)}%")
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onClose) { Text("Fechar") } },
        dismissButton = { TextButton(onClick = onDelete) { Text("Excluir", color = Color.Red) } }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}