package com.example.calculadoraimc.feature.home.view

// --- CORREÇÃO DOS IMPORTS ---
// Importamos os componentes da pasta correta
import com.example.calculadoraimc.feature.home.components.IMCCalculatorContainer
import com.example.calculadoraimc.feature.home.components.MainCard
import com.example.calculadoraimc.feature.home.components.MetricCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.feature.home.model.MetricCardData
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    // Callback para avisar a MainActivity que houve um cálculo
    onCalculate: (IMCData, Double, Double) -> Unit = { _, _, _ -> }
) {
    var resultIMC by remember { mutableStateOf<IMCData?>(null) }
    var heightUser by remember { mutableDoubleStateOf(0.0) }
    var weightUser by remember { mutableDoubleStateOf(0.0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Calculadora de IMC")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Entrada de dados
            IMCCalculatorContainer(
                onResult = { result, weight, height ->
                    resultIMC = result
                    weightUser = weight
                    heightUser = height

                    // Avisa quem chamou (MainActivity) para salvar no banco
                    onCalculate(result, weight, height)
                }
            )

            Spacer(Modifier.height(28.dp))

            // Exibe o conteúdo se houver resultado
            resultIMC?.let {
                HomeContent(it, weightUser, heightUser)
            }
        }
    }
}

// --- A FUNÇÃO QUE ESTAVA FALTANDO ---
@Composable
fun HomeContent(result: IMCData, weight: Double, height: Double) {

    // Card Principal (Azul)
    MainCard(result)

    Spacer(Modifier.height(28.dp))

    // Cards de altura e peso (Verde e Amarelo)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MetricCard(
            modifier = Modifier.weight(1f),
            metrics = MetricCardData.Height(height.toFloat()),
        )
        MetricCard(
            modifier = Modifier.weight(1f),
            metrics = MetricCardData.Weight(weight.toFloat()),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CalculadoraIMCTheme {
        Home()
    }
}