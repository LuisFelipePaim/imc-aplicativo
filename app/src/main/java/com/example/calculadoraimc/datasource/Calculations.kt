package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.IMCData
import kotlin.math.log10

object Calculations {

    @SuppressLint("DefaultLocale")
    fun calculateIMC(
        height: String,
        weight: String,
        age: String,
        isMale: Boolean,
        activityFactor: Double,
        neck: String = "",
        waist: String = "",
        hip: String = "",
        response: (IMCData, Double, Double, Double, Double, Double) -> Unit
    ) {
        if (height.isNotEmpty() && weight.isNotEmpty() && age.isNotEmpty()) {
            val heightFormatted = height.replace(",", ".").toDoubleOrNull()
            val weightFormatted = weight.replace(",", ".").toDoubleOrNull()
            val ageFormatted = age.toIntOrNull()

            val neckFormatted = neck.replace(",", ".").toDoubleOrNull() ?: 0.0
            val waistFormatted = waist.replace(",", ".").toDoubleOrNull() ?: 0.0
            val hipFormatted = hip.replace(",", ".").toDoubleOrNull() ?: 0.0

            if (weightFormatted != null && heightFormatted != null && heightFormatted > 0 && ageFormatted != null) {
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

                val tmb = if (isMale) {
                    (10 * weightFormatted) + (6.25 * heightFormatted) - (5 * ageFormatted) + 5
                } else {
                    (10 * weightFormatted) + (6.25 * heightFormatted) - (5 * ageFormatted) - 161
                }

                val tdee = tmb * activityFactor

                var bodyFat = 0.0
                if (neckFormatted > 0 && waistFormatted > 0) {
                    if (isMale) {
                       if (waistFormatted > neckFormatted) {
                            val log1 = log10(waistFormatted - neckFormatted)
                            val log2 = log10(heightFormatted)
                            val density = 1.0324 - (0.19077 * log1) + (0.1554 * log2)
                            bodyFat = (495 / density) - 450
                        }
                    } else {
                        // Mulheres: precisa do quadril
                        if (hipFormatted > 0) {
                            val log1 = log10(waistFormatted + hipFormatted - neckFormatted)
                            val log2 = log10(heightFormatted)
                            val density = 1.29579 - (0.35004 * log1) + (0.22100 * log2)
                            bodyFat = (495 / density) - 450
                        }
                    }
                }

                if (bodyFat < 0) bodyFat = 0.0

                response(imcData, weightFormatted, heightFormatted, tmb, tdee, bodyFat)

            } else {
                response(IMCData("null", "Inválido", 0.0), 0.0, 0.0, 0.0, 0.0, 0.0)
            }
        } else {
            response(IMCData("null", "Preencha", 0.0), 0.0, 0.0, 0.0, 0.0, 0.0)
        }
    }
}