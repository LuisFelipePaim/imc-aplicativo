package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.IMCData

object Calculations {

    // Data class auxiliar para retornar tudo de uma vez
    data class CalculationResult(
        val imcData: IMCData,
        val weight: Double,
        val height: Double,
        val idealWeightMin: Double,
        val idealWeightMax: Double
    )

    @SuppressLint("DefaultLocale")
    fun calculateComplete(
        heightStr: String,
        weightStr: String,
        onResult: (CalculationResult) -> Unit
    ) {
        if (heightStr.isNotEmpty() && weightStr.isNotEmpty()) {
            val h = heightStr.replace(",", ".").toDoubleOrNull()
            val w = weightStr.replace(",", ".").toDoubleOrNull()

            if (w != null && h != null && h > 0) {
                val heightM = h / 100
                val imc = w / (heightM * heightM)
                val imcFormatted = String.format("%.2f", imc)

                // Texto e Classificação
                val (text, type) = when {
                    imc < 18.5 -> "Abaixo do peso" to "atencao"
                    imc < 25 -> "Peso normal" to "normal"
                    imc < 30 -> "Sobrepeso" to "atencao"
                    imc < 35 -> "Obesidade Grau I" to "critico"
                    imc < 40 -> "Obesidade Grau II" to "critico"
                    else -> "Obesidade Grau III" to "critico"
                }

                val imcData = IMCData(imcFormatted, text, imc)

                // Cálculo Peso Ideal (IMC 18.5 a 24.9)
                val idealMin = 18.5 * (heightM * heightM)
                val idealMax = 24.9 * (heightM * heightM)

                onResult(CalculationResult(imcData, w, h, idealMin, idealMax))
            }
        }
    }

    // Mantive a função antiga caso algo ainda a use, mas redirecionando
    fun calculateIMC(height: String, weight: String, response: (IMCData, Double, Double) -> Unit) {
        calculateComplete(height, weight) { res ->
            response(res.imcData, res.weight, res.height)
        }
    }
}