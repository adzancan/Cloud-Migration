package com.example.cloudmigration.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cloudmigration.models.UniFiDevice
import com.example.cloudmigration.ui.icons.AppIcons
import com.example.cloudmigration.ui.theme.DarkCardBg
import com.example.cloudmigration.ui.theme.DarkCardBorder
import com.example.cloudmigration.ui.theme.PrimaryBlue
import com.example.cloudmigration.ui.theme.TextMuted

data class DeviceMigrationState(
    val device: UniFiDevice,
    val isRunning: Boolean = false,
    val isCompleted: Boolean = false,
    val isSuccess: Boolean = false,
    val infoOutput: String = "",
    val error: String? = null
)

@Composable
fun MigrationProgressDialog(
    states: List<DeviceMigrationState>,
    isFinished: Boolean,
    onClose: () -> Unit
) {
    Dialog(onDismissRequest = { if (isFinished) onClose() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .clip(RoundedCornerShape(24.dp))
                .background(DarkCardBg)
                .border(1.dp, DarkCardBorder, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.Cloud,
                        contentDescription = "Migration",
                        tint = PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = if (isFinished) "Migração Concluída" else "Migrando Dispositivos...",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Executando set-inform e validando comando 'info' via SSH:",
                    color = TextMuted,
                    fontSize = 13.sp
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(states, key = { it.device.id }) { state ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    when {
                                        state.isRunning -> PrimaryBlue
                                        state.isCompleted && state.isSuccess -> Color(0xFF10B981)
                                        state.isCompleted -> Color(0xFFEF4444)
                                        else -> DarkCardBorder
                                    },
                                    RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${state.device.name} (${state.device.ip})",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    when {
                                        state.isRunning -> {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(16.dp),
                                                color = PrimaryBlue,
                                                strokeWidth = 2.dp
                                            )
                                        }
                                        state.isCompleted && state.isSuccess -> {
                                            Text("SUCESSO ✓", color = Color(0xFF10B981), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                        state.isCompleted -> {
                                            Text("FALHA ✗", color = Color(0xFFEF4444), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }

                                if (state.infoOutput.isNotBlank()) {
                                    Text(
                                        text = state.infoOutput.trim(),
                                        color = TextMuted,
                                        fontSize = 11.sp,
                                        maxLines = 4
                                    )
                                }

                                if (state.error != null) {
                                    Text(
                                        text = "Erro: ${state.error}",
                                        color = Color(0xFFF87171),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }

                if (isFinished) {
                    Button(
                        onClick = onClose,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Text("Fechar", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
