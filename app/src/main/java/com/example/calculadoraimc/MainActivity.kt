package com.example.calculadoraimc.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.calculadoraimc.database.AppDatabase
import com.example.calculadoraimc.database.IMCResultEntity
import com.example.calculadoraimc.feature.history.HistoryScreen
import com.example.calculadoraimc.feature.home.view.Home
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inicializa o Banco de Dados
        val db = AppDatabase.getDatabase(this)
        val dao = db.imcDao()

        setContent {
            CalculadoraIMCTheme {
                // Passamos o DAO para a tela principal
                MainScreen(dao)
            }
        }
    }
}

@Composable
fun MainScreen(dao: com.example.calculadoraimc.database.IMCDao) {
    // Controla qual aba está visível: 0 = Home, 1 = Histórico
    var currentTab by remember { mutableIntStateOf(0) }

    // Lê os dados do banco em tempo real
    val historyList by dao.getAllHistory().collectAsState(initial = emptyList())

    // Escopo para salvar/deletar no banco (Background Thread)
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
                    label = { Text("Início") },
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Rounded.History, contentDescription = "Histórico") },
                    label = { Text("Histórico") },
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 }
                )
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            if (currentTab == 0) {
                // TELA HOME
                // Precisamos passar um evento de "Salvar" para a Home
                Home(
                    onCalculate = { result, weight, height ->
                        scope.launch(Dispatchers.IO) {
                            // Cálculo simples do Peso Ideal para salvar
                            val hM = height / 100
                            val minW = 18.5 * (hM * hM)
                            val maxW = 24.9 * (hM * hM)

                            val entity = IMCResultEntity(
                                weight = weight,
                                height = height,
                                imc = result.valueLiteral,
                                classification = result.text,
                                idealWeightMin = minW,
                                idealWeightMax = maxW
                            )
                            dao.insert(entity)
                        }
                    }
                )
            } else {
                // TELA HISTÓRICO
                HistoryScreen(
                    historyList = historyList,
                    onDelete = { item ->
                        scope.launch(Dispatchers.IO) {
                            dao.delete(item)
                        }
                    }
                )
            }
        }
    }
}