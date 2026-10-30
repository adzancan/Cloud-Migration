package com.example.cloudmigration.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cloudmigration.ui.icons.AppIcons
import com.example.cloudmigration.ui.theme.DarkCardBg
import com.example.cloudmigration.ui.theme.DarkCardBorder
import com.example.cloudmigration.ui.theme.PrimaryBlue
import com.example.cloudmigration.ui.theme.TextMuted

@Composable
fun MigrationBar(
    selectedCount: Int,
    cloudIp: String,
    onMigrate: () -> Unit,
    onRestoreDefault: () -> Unit
) {
    if (selectedCount == 0) return

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .border(1.dp, DarkCardBorder, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        color = DarkCardBg,
        shadowElevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$selectedCount dispositivo(s) selecionado(s)",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Alvo: ${cloudIp.ifBlank { "Não configurado" }}",
                    color = TextMuted,
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Restore Default Button
                OutlinedButton(
                    onClick = onRestoreDefault,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF38BDF8)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF38BDF8)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    Text(
                        text = "Restaurar Padrão",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }

                // Migrate to Cloud Button
                    Button(
                    onClick = onMigrate,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = AppIcons.Cloud,
                            contentDescription = "Migrate",
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Migrar Nuvem",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
