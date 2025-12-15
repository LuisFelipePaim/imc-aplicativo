package com.example.calculadoraimc.datasource

object InputValidator {

    // Retorna null se estiver válido, ou uma mensagem de erro String se falhar
    fun validateHeight(height: String): String? {
        if (height.isBlank()) return "Campo obrigatório"

        val h = height.replace(",", ".").toDoubleOrNull()
        return when {
            h == null -> "Digite apenas números"
            h < 50.0 -> "Altura muito baixa (min 50cm)"
            h > 300.0 -> "Altura irrealista (max 300cm)"
            else -> null
        }
    }

    fun validateWeight(weight: String): String? {
        if (weight.isBlank()) return "Campo obrigatório"

        val w = weight.replace(",", ".").toDoubleOrNull()
        return when {
            w == null -> "Digite apenas números"
            w < 2.0 -> "Peso muito baixo"
            w > 600.0 -> "Peso irrealista"
            else -> null
        }
    }

    fun validateAge(age: String): String? {
        if (age.isBlank()) return "Campo obrigatório"

        val a = age.toIntOrNull()
        return when {
            a == null -> "Digite um número inteiro"
            a < 1 -> "Idade inválida"
            a > 120 -> "Idade irrealista"
            else -> null
        }
    }
}