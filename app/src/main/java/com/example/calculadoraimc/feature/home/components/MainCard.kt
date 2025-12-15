package com.example.calculadoraimc.feature.home.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.components.IMCGraphic
import com.example.calculadoraimc.feature.home.components.IconTag
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.ui.theme.BlackFont
import com.example.calculadoraimc.ui.theme.BlueColor
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

@Composable
fun MainCard(result: IMCData) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BlueColor
        ),
        shape = RoundedCornerShape(
            size = 36.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp, 24.dp, 20.dp, 40.dp)
                .fillMaxWidth()
        ) {
            // PRIMEIRA COLUNA
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                // Tag superior
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconTag(
                        icon = rememberVectorPainter(Icons.Rounded.FavoriteBorder),
                        contentDescription = "Icone superior"
                    )
                    Text(
                        text = "Seu IMC",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp
                        ),
                        color = BlackFont,
                        softWrap = false
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = result.value,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 56.sp
                    ),
                    color = BlackFont
                )

                Text(
                    text = result.text,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 24.sp
                    )
                )
            }

            // SEGUNDA COLUNA
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                IMCGraphic(result.valueLiteral)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MainCardPreview() {
    CalculadoraIMCTheme {
        MainCard(IMCData("44.4", "Teste", 0.0))
    }
}