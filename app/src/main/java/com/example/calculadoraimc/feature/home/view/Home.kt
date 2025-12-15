package com.example.calculadoraimc.feature.home.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// --- IMPORTS CRÍTICOS QUE FALTAVAM ---
import com.example.calculadoraimc.datasource.CsvExporter
import com.example.calculadoraimc.datasource.NotificationReceiver
import com.example.calculadoraimc.feature.home.components.IMCCalculatorContainer
import com.example.calculadoraimc.feature.home.components.MainCard
import com.example.calculadoraimc.feature.home.components.MetricCard
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.feature.home.model.MetricCardData
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.example.calculadoraimc.database.IMCResultEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    historyListForExport: List<IMCResultEntity> = emptyList(),
    onCalculate: (IMCData, Double, Double, Double, Double) -> Unit = { _, _, _, _, _ -> }
) {
    var resultIMC by remember { mutableStateOf<IMCData?>(null) }
    var heightUser by remember { mutableDoubleStateOf(0.0) }
    var weightUser by remember { mutableDoubleStateOf(0.0) }
    var tmbUser by remember { mutableDoubleStateOf(0.0) }
    var tdeeUser by remember { mutableDoubleStateOf(0.0) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Calculadora de IMC") },
                actions = {
                    IconButton(onClick = {
                        if (historyListForExport.isNotEmpty()) {
                            CsvExporter.exportAndShare(context, historyListForExport)
                        } else {
                            Toast.makeText(context, "Histórico vazio!", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(Icons.Rounded.Download, contentDescription = "Exportar")
                    }
                    IconButton(onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                                NotificationReceiver.scheduleDaily(context)
                                Toast.makeText(context, "Lembrete Agendado!", Toast.LENGTH_SHORT).show()
                            } else {
                                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
                            }
                        } else {
                            NotificationReceiver.scheduleDaily(context)
                            Toast.makeText(context, "Lembrete Agendado!", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(Icons.Rounded.Notifications, contentDescription = "Lembrete")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            IMCCalculatorContainer(
                onResult = { result, w, h, tmb, tdee ->
                    resultIMC = result
                    weightUser = w
                    heightUser = h
                    tmbUser = tmb
                    tdeeUser = tdee
                    onCalculate(result, w, h, tmb, tdee)
                }
            )

            Spacer(Modifier.height(28.dp))

            resultIMC?.let {
                HomeContent(it, weightUser, heightUser, tmbUser, tdeeUser)
            }
        }
    }
}

// --- ESTA FUNÇÃO ESTAVA FALTANDO ---
@Composable
fun HomeContent(result: IMCData, weight: Double, height: Double, tmb: Double, tdee: Double) {
    MainCard(result)
    Spacer(Modifier.height(28.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        MetricCard(Modifier.weight(1f), MetricCardData.Height(height.toFloat()))
        MetricCard(Modifier.weight(1f), MetricCardData.Weight(weight.toFloat()))
    }

    if (tmb > 0) {
        Spacer(Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(Modifier.weight(1f), MetricCardData.Tmb(tmb.toFloat()))
            MetricCard(Modifier.weight(1f), MetricCardData.Tdee(tdee.toFloat()))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CalculadoraIMCTheme {
        Home()
    }
}