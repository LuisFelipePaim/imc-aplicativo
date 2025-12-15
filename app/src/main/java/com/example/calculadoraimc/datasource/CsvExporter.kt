package com.example.calculadoraimc.datasource

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.calculadoraimc.database.IMCResultEntity
import java.io.File
import java.io.FileWriter

object CsvExporter {

    fun exportAndShare(context: Context, history: List<IMCResultEntity>) {
        if (history.isEmpty()) return

        val fileName = "historico_imc.csv"
        val file = File(context.cacheDir, fileName)

        try {
            val writer = FileWriter(file)
            writer.append("Data,Peso,Altura,IMC,Classificacao,TMB,TDEE\n")

            history.forEach { item ->
                writer.append("${item.getFormattedDate()},")
                writer.append("${item.weight},")
                writer.append("${item.height},")
                writer.append("${String.format("%.2f", item.imc)},")
                writer.append("${item.classification},")
                writer.append("${item.tmb},")
                writer.append("${item.tdee}\n")
            }
            writer.flush()
            writer.close()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_SUBJECT, "Histórico de IMC")
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(intent, "Exportar para:"))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}