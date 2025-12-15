package com.example.calculadoraimc.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "imc_history")
data class IMCResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val weight: Double,
    val height: Double,
    val imc: Double,
    val classification: String,
    val tmb: Double = 0.0,
    val tdee: Double = 0.0,

    // --- NOVOS CAMPOS PARA GORDURA ---
    val bodyFat: Double = 0.0,
    val waist: Double = 0.0,
    val neck: Double = 0.0,
    val hip: Double = 0.0, // Apenas para mulheres (0.0 para homens)

    val idealWeightMin: Double = 0.0,
    val idealWeightMax: Double = 0.0
) {
    fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(date))
    }
}