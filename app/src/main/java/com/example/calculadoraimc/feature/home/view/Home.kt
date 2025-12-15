package com.example.calculadoraimc.feature.home.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.calculadoraimc.datasource.CsvExporter
import com.example.calculadoraimc.datasource.NotificationReceiver
import com.example.calculadoraimc.feature.home.components.IMCCalculatorContainer
import com.example.calculadoraimc.feature.home.components.MainCard
import com.example.calculadoraimc.feature.home.components.MetricCard
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.feature.home.model.MetricCardData
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.example.calculadoraimc.ui.theme.HealthPrimary
import com.example.calculadoraimc.ui.theme.HealthSecondary
import com.example.calculadoraimc.database.IMCResultEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Home(
    historyListForExport: List<IMCResultEntity> = emptyList(),
    onCalculate: (IMCData, Double, Double, Double, Double, Double) -> Unit = { _, _, _, _, _, _ -> }
) {
    var resultIMC by remember { mutableStateOf<IMCData?>(null) }
    var heightUser by remember { mutableDoubleStateOf(0.0) }
    var weightUser by remember { mutableDoubleStateOf(0.0) }
    var tmbUser by remember { mutableDoubleStateOf(0.0) }
    var tdeeUser by remember { mutableDoubleStateOf(0.0) }
    var bodyFatUser by remember { mutableDoubleStateOf(0.0) }

    val context = LocalContext.current
    // Data formatada (ex: Segunda, 15 de Dezembro)
    val currentDate = remember {
        SimpleDateFormat("EEEE, d 'de' MMMM", Locale("pt", "BR")).format(Date())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fundo cinza bem claro
    ) {
        // --- 1. CABEÇALHO VERDE CURVO (Igual ao da imagem Nature) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp) // Mais alto para caber os botões
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(HealthPrimary, HealthSecondary)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            // Texto da Data
            Text(
                text = currentDate.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.8f)
            )

            // Título Principal
            Text(
                text = "Calculadora IMC",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- 2. NOVOS BOTÕES "PILL SHAPE" (Brancos no fundo Verde) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botão Exportar
                Button(
                    onClick = {
                        if (historyListForExport.isNotEmpty()) CsvExporter.exportAndShare(context, historyListForExport)
                        else Toast.makeText(context, "Histórico vazio!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White, // Botão Branco
                        contentColor = HealthSecondary // Texto Verde
                    ),
                    shape = RoundedCornerShape(50), // Totalmente arredondado
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(Icons.Rounded.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Exportar", fontWeight = FontWeight.Bold)
                }

                // Botão Lembrete
                Button(
                    onClick = {
                        NotificationReceiver.scheduleDaily(context)
                        Toast.makeText(context, "Lembrete Ativo!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = HealthSecondary
                    ),
                    shape = RoundedCornerShape(50),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(Icons.Rounded.Notifications, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Alertas", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. CARD FLUTUANTE (Inputs) ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Seus Dados",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    IMCCalculatorContainer(
                        onResult = { result, w, h, tmb, tdee, fat ->
                            resultIMC = result
                            weightUser = w
                            heightUser = h
                            tmbUser = tmb
                            tdeeUser = tdee
                            bodyFatUser = fat
                            onCalculate(result, w, h, tmb, tdee, fat)
                        }
                    )
                }
            }

            // --- 4. RESULTADOS ---
            resultIMC?.let {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Resultado",
                    style = MaterialTheme.typography.headlineSmall,
                    color = HealthSecondary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(12.dp))

                MainCard(it) // Card Verde Grande

                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard(Modifier.weight(1f), MetricCardData.Height(heightUser.toFloat()))
                    MetricCard(Modifier.weight(1f), MetricCardData.Weight(weightUser.toFloat()))
                }

                if (tmbUser > 0) {
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricCard(Modifier.weight(1f), MetricCardData.Tmb(tmbUser.toFloat()))
                        MetricCard(Modifier.weight(1f), MetricCardData.Tdee(tdeeUser.toFloat()))
                    }
                    if (bodyFatUser > 0) {
                        Spacer(Modifier.height(10.dp))
                        MetricCard(Modifier.fillMaxWidth(), MetricCardData.BodyFat(bodyFatUser.toFloat()))
                    }
                }
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CalculadoraIMCTheme { Home() }
}