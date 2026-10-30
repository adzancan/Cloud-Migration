package com.example.cloudmigration.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cloudmigration.models.UniFiDevice
import com.example.cloudmigration.ui.icons.AppIcons
import com.example.cloudmigration.ui.theme.DarkCardBg
import com.example.cloudmigration.ui.theme.DarkCardBorder
import com.example.cloudmigration.ui.theme.PrimaryBlue
import com.example.cloudmigration.ui.theme.TextMuted

@Composable
fun DeviceListSection(
    devices: List<UniFiDevice>,
    hasScanned: Boolean,
    onStartScan: () -> Unit,
    onToggleSelect: (UniFiDevice) -> Unit,
    onSelectAll: () -> Unit,
    onDeselectAll: () -> Unit,
    onRevalidate: (UniFiDevice) -> Unit,
    onCopyIp: (String) -> Unit,
    onCopyMac: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCount = devices.count { it.isSelected }
    val allSelected = devices.isNotEmpty() && selectedCount == devices.size

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Section Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dispositivos UniFi (${devices.size})",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            if (devices.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$selectedCount selecionados",
                        color = PrimaryBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = if (allSelected) "Desmarcar Todos" else "Selecionar Todos",
                        color = PrimaryBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            if (allSelected) onDeselectAll() else onSelectAll()
                        }
                    )
                }
            }
        }

        // Empty State vs Device List
        if (devices.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .border(1.dp, DarkCardBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardBg)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Router/AP icon in dark blue circle
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = AppIcons.Router,
                            contentDescription = "No Devices",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = "No UniFi devices found",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Start a new scan or edit the subnet in settings if needed.",
                        color = TextMuted,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Button(
                        onClick = onStartScan,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .width(140.dp)
                            .height(44.dp)
                    ) {
                        Text(
                            text = "Start Scan",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(devices, key = { it.id }) { device ->
                    DeviceCard(
                        device = device,
                        onToggleSelect = onToggleSelect,
                        onRevalidate = onRevalidate,
                        onCopyIp = onCopyIp,
                        onCopyMac = onCopyMac
                    )
                }
            }
        }
    }
}
