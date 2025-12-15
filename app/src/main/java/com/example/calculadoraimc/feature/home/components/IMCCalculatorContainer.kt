package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.datasource.InputValidator
import com.example.calculadoraimc.feature.home.model.IMCData

@Composable
fun IMCCalculatorContainer(
    onResult: (IMCData, Double, Double, Double, Double) -> Unit
) {
    // Estados dos Valores
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }

    // Estados de Erro (Validação)
    var heightError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var ageError by remember { mutableStateOf<String?>(null) }

    // Dropdown State
    var expanded by remember { mutableStateOf(false) }
    val activityOptions = listOf(
        "Sedentário" to 1.2,
        "Leve (1-3 dias)" to 1.375,
        "Moderado (3-5 dias)" to 1.55,
        "Intenso (6-7 dias)" to 1.725
    )
    var selectedOption by remember { mutableStateOf(activityOptions[0]) }

    Column {
        // LINHA 1: Altura e Peso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Top // Alinha no topo caso um campo tenha erro e o outro não
        ) {
            // Input Altura
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = height,
                onValueChange = {
                    if (it.length <= 3) {
                        height = it
                        heightError = null // Limpa erro ao digitar
                    }
                },
                label = { Text("Altura (cm)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = heightError != null,
                supportingText = {
                    heightError?.let { msg -> Text(text = msg, color = MaterialTheme.colorScheme.error) }
                },
                trailingIcon = {
                    if (heightError != null) Icon(Icons.Filled.Error, "Erro", tint = MaterialTheme.colorScheme.error)
                }
            )

            // Input Peso
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = weight,
                onValueChange = {
                    if (it.length <= 7) {
                        weight = it
                        weightError = null // Limpa erro ao digitar
                    }
                },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                isError = weightError != null,
                supportingText = {
                    weightError?.let { msg -> Text(text = msg, color = MaterialTheme.colorScheme.error) }
                },
                trailingIcon = {
                    if (weightError != null) Icon(Icons.Filled.Error, "Erro", tint = MaterialTheme.colorScheme.error)
                }
            )
        }

        Spacer(Modifier.height(8.dp))

        // LINHA 2: Idade e Sexo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Input Idade
            OutlinedTextField(
                modifier = Modifier.weight(0.8f),
                shape = RoundedCornerShape(16.dp),
                value = age,
                onValueChange = {
                    if (it.length <= 3) {
                        age = it
                        ageError = null // Limpa erro ao digitar
                    }
                },
                label = { Text("Idade") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                isError = ageError != null,
                supportingText = {
                    ageError?.let { msg -> Text(text = msg, color = MaterialTheme.colorScheme.error) }
                },
                trailingIcon = {
                    if (ageError != null) Icon(Icons.Filled.Error, "Erro", tint = MaterialTheme.colorScheme.error)
                }
            )

            // Seletor de Sexo
            // Adicionado padding top para alinhar visualmente com o TextField que tem label
            Row(
                modifier = Modifier
                    .weight(1.2f)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                FilterChip(
                    selected = isMale,
                    onClick = { isMale = true },
                    label = { Text("Masc") }
                )
                Spacer(Modifier.width(4.dp))
                FilterChip(
                    selected = !isMale,
                    onClick = { isMale = false },
                    label = { Text("Fem") }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // LINHA 3: Dropdown
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedOption.first,
                onValueChange = {},
                readOnly = true,
                label = { Text("Nível de Atividade") },
                trailingIcon = {
                    Icon(if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown, "Dropdown")
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Box(modifier = Modifier.matchParentSize().clickable { expanded = true })

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                activityOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option.first) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Botão Calcular
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                // 1. Executa Validações
                val hError = InputValidator.validateHeight(height)
                val wError = InputValidator.validateWeight(weight)
                val aError = InputValidator.validateAge(age)

                // 2. Atualiza estados de erro
                heightError = hError
                weightError = wError
                ageError = aError

                // 3. Se não houver erros, calcula
                if (hError == null && wError == null && aError == null) {
                    Calculations.calculateIMC(
                        height = height,
                        weight = weight,
                        age = age,
                        isMale = isMale,
                        activityFactor = selectedOption.second,
                        response = { result, w, h, tmb, tdee ->
                            onResult(result, w, h, tmb, tdee)
                        }
                    )
                }
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