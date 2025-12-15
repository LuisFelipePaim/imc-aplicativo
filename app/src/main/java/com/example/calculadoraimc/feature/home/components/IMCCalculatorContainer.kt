package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@Composable
fun IMCCalculatorContainer(
    // Atualizamos a assinatura para devolver IMC, Peso e Altura
    onResult: (IMCData, Double, Double) -> Unit
) {

    var height by remember { mutableStateOf<String>("") }
    var weight by remember { mutableStateOf<String>("") }
    val errorField by remember { mutableStateOf<Boolean>(false) }

    Column {
        // Inputs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Input da altura
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = height,
                onValueChange = {
                    if (it.length <= 3) height = it
                },
                label = { Text(text = "Altura em cm", fontSize = 14.sp) },
                keyboardOptions = KeyboardOptions(
                    // CORREÇÃO: Removido NumberPassword
                    keyboardType = KeyboardType.Number
                )
            )

            // Input do peso
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = weight,
                onValueChange = {
                    if (it.length <= 7) weight = it
                },
                label = { Text(text = "Peso em kg", fontSize = 14.sp) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                isError = errorField
            )
        }

        Spacer(Modifier.height(8.dp))

        // Botao
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // Passamos os 3 valores recebidos para a tela pai
                Calculations.calculateIMC(height = height, weight = weight, response = { result, pesoValidado, alturaValidada ->
                    onResult(result, pesoValidado, alturaValidada)
                })
            },
            colors = ButtonColors(
                containerColor = Color(156, 39, 176, 255),
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            )
        ) {
            Text(text = "Calcular", fontSize = 20.sp, color = Color.White)
        }
    }
}

@Preview
@Composable
private fun IMCCalculatorContainerPreview() {
    CalculadoraIMCTheme {
        IMCCalculatorContainer { _, _, _ -> }
    }
}