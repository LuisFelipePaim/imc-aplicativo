package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.feature.home.model.IMCData

@Composable
fun IMCCalculatorContainer(
    onResult: (IMCData, Double, Double, Double, Double, Double) -> Unit
) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }

    var neck by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hip by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val activityOptions = listOf("Sedentário" to 1.2, "Leve" to 1.375, "Moderado" to 1.55, "Intenso" to 1.725)
    var selectedOption by remember { mutableStateOf(activityOptions[0]) }

    var isHeightError by remember { mutableStateOf(false) }
    var isWeightError by remember { mutableStateOf(false) }
    var isAgeError by remember { mutableStateOf(false) }

    fun validateFields(): Boolean {
        val hValue = height.trim().toIntOrNull()
        val wValue = weight.replace(",", ".").trim().toDoubleOrNull()
        val aValue = age.trim().toIntOrNull()

        isHeightError = hValue == null || hValue < 50 || hValue > 300
        isWeightError = wValue == null || wValue < 2.0 || wValue > 600.0
        isAgeError = aValue == null || aValue < 0 || aValue > 130

        return !(isHeightError || isWeightError || isAgeError)
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = height,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                        height = newValue
                        isHeightError = false
                    }
                },
                label = { Text("Altura (cm)") },
                placeholder = { Text("Ex: 175") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isHeightError,
                supportingText = { if (isHeightError) Text("Use CM (50-300)", color = MaterialTheme.colorScheme.error) }
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = weight,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() || it == '.' || it == ',' } && newValue.length <= 6) {
                        weight = newValue
                        isWeightError = false
                    }
                },
                label = { Text("Peso (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isWeightError,
                supportingText = { if (isWeightError) Text("Inválido (2-600kg)", color = MaterialTheme.colorScheme.error) }
            )
        }
        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                modifier = Modifier.weight(0.8f),
                shape = RoundedCornerShape(16.dp),
                value = age,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                        age = newValue
                        isAgeError = false
                    }
                },
                label = { Text("Idade") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isAgeError,
                supportingText = { if (isAgeError) Text("0-130 anos", color = MaterialTheme.colorScheme.error) }
            )
            Row(modifier = Modifier.weight(1.2f), horizontalArrangement = Arrangement.SpaceEvenly) {
                FilterChip(selected = isMale, onClick = { isMale = true }, label = { Text("Masc") })
                FilterChip(selected = !isMale, onClick = { isMale = false }, label = { Text("Fem") })
            }
        }
        Spacer(Modifier.height(16.dp))

        Text("Medidas Opcionais (Gordura %)", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = neck, onValueChange = { if (it.all { char -> char.isDigit() || char == '.' || char == ',' }) neck = it },
                label = { Text("Pescoço") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                value = waist, onValueChange = { if (it.all { char -> char.isDigit() || char == '.' || char == ',' }) waist = it },
                label = { Text("Cintura") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (!isMale) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    value = hip, onValueChange = { if (it.all { char -> char.isDigit() || char == '.' || char == ',' }) hip = it },
                    label = { Text("Quadril") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedOption.first, onValueChange = {}, readOnly = true, label = { Text("Atividade") },
                trailingIcon = { Icon(if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown, "Drop") },
                shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().clickable { expanded = true }, enabled = false,
                colors = OutlinedTextFieldDefaults.colors(disabledTextColor = MaterialTheme.colorScheme.onSurface, disabledBorderColor = MaterialTheme.colorScheme.outline, disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Box(modifier = Modifier.matchParentSize().clickable { expanded = true })
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.fillMaxWidth(0.9f)) {
                activityOptions.forEach { option ->
                    DropdownMenuItem(text = { Text(option.first) }, onClick = { selectedOption = option; expanded = false })
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (validateFields()) {
                    Calculations.calculateIMC(
                        height, weight.replace(",", "."), age, isMale, selectedOption.second,
                        neck.replace(",", "."), waist.replace(",", "."), hip.replace(",", "."),
                        response = { res, w, h, tmb, tdee, fat ->
                            onResult(res, w, h, tmb, tdee, fat)
                        }
                    )
                }
            },
            colors = ButtonColors(containerColor = Color(156, 39, 176, 255), contentColor = Color.White, disabledContainerColor = Color.Gray, disabledContentColor = Color.White)
        ) {
            Text(text = "Calcular", fontSize = 20.sp, color = Color.White)
        }
    }
}