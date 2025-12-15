package com.example.calculadoraimc.feature.home.model

data class IMCData(
    val valueLiteral: String, // Texto (ex: "24,5")
    val text: String,         // Classificação (ex: "Normal")
    val value: Double         // Número (ex: 24.5) <- ESSENCIAL
)