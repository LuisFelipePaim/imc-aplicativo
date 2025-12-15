package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.IMCData

object Calculations {

    @SuppressLint("DefaultLocale")
    fun calculateIMC(
        height: String,
        weight: String,
        age: String,
        isMale: Boolean,
        activityFactor: Double, // <--- Novo Parâmetro (1.2, 1.375, etc)
        response: (IMCData, Double, Double, Double, Double) -> Unit // Retorna +1 Double (TDEE)
    ) {
        if (height.isNotEmpty() && weight.isNotEmpty() && age.isNotEmpty()) {
            val heightFormatted = height.replace(",", ".").toDoubleOrNull()
            val weightFormatted = weight.replace(",", ".").toDoubleOrNull()
            val ageFormatted = age.toIntOrNull()

            if (weightFormatted != null && heightFormatted != null && heightFormatted > 0 && ageFormatted != null) {
                // 1. IMC
                val alturaEmMetros = heightFormatted / 100
                val imc = weightFormatted / (alturaEmMetros * alturaEmMetros)
                val imcFormate = String.format("%.2f", imc)

                val imcData = when {
                    imc < 18.5 -> IMCData(imcFormate, "Abaixo do peso", imc)
                    imc < 25 -> IMCData(imcFormate, "Peso normal", imc)
                    imc < 30 -> IMCData(imcFormate, "Sobrepeso", imc)
                    imc < 35 -> IMCData(imcFormate, "Obesidade Grau I", imc)
                    imc < 40 -> IMCData(imcFormate, "Obesidade Grau II", imc)
                    else -> IMCData(imcFormate, "Obesidade Grau III", imc)
                }

                // 2. TMB (Mifflin-St Jeor)
                val tmb = if (isMale) {
                    (10 * weightFormatted) + (6.25 * heightFormatted) - (5 * ageFormatted) + 5
                } else {
                    (10 * weightFormatted) + (6.25 * heightFormatted) - (5 * ageFormatted) - 161
                }

                // 3. TDEE (Necessidade Calórica)
                val tdee = tmb * activityFactor

                // Retorna: IMC, Peso, Altura, TMB, TDEE
                response(imcData, weightFormatted, heightFormatted, tmb, tdee)

            } else {
                response(IMCData("null", "Valores inválidos", 0.0), 0.0, 0.0, 0.0, 0.0)
            }
        } else {
            response(IMCData("null", "Preencha os campos", 0.0), 0.0, 0.0, 0.0, 0.0)
        }
    }
}