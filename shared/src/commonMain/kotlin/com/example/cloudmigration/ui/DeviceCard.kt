package com.example.cloudmigration.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.cloudmigration.models.DeviceType
import com.example.cloudmigration.models.UniFiDevice
import com.example.cloudmigration.ui.icons.AppIcons
import com.example.cloudmigration.ui.theme.DarkCardBg
import com.example.cloudmigration.ui.theme.DarkCardBorder
import com.example.cloudmigration.ui.theme.PrimaryBlue
import com.example.cloudmigration.ui.theme.TextMuted

@Composable
fun DeviceCard(
    device: UniFiDevice,
    onToggleSelect: (UniFiDevice) -> Unit,
    onRevalidate: (UniFiDevice) -> Unit,
    onCopyIp: (String) -> Unit,
    onCopyMac: (String) -> Unit
) {
    val isSelected = device.isSelected
    val cardBorderColor = when {
        device.isMigratedToCloud -> Color(0xFF10B981) // Green when migrated to cloud
        isSelected -> PrimaryBlue
        else -> DarkCardBorder
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 3.dp)
            .border(1.dp, cardBorderColor, RoundedCornerShape(12.dp))
            .clickable { onToggleSelect(device) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Checkbox
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isSelected) PrimaryBlue else Color.Transparent)
                            .border(
                                2.dp,
                                if (isSelected) PrimaryBlue else TextMuted,
                                RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Icon(
                                imageVector = AppIcons.Check,
                                contentDescription = "Checked",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }

                    // Device Icon
                    val deviceIcon = when (device.type) {
                        DeviceType.GATEWAY -> AppIcons.Router
                        DeviceType.SWITCH -> AppIcons.Switch
                        DeviceType.AP -> AppIcons.Wifi
                        DeviceType.CONTROLLER -> AppIcons.Server
                        DeviceType.CAMERA_DOORBELL -> AppIcons.Camera
                        else -> AppIcons.Wifi
                    }

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = deviceIcon,
                            contentDescription = "Device Type",
                            tint = if (device.isMigratedToCloud) Color(0xFF10B981) else PrimaryBlue,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    // Name and Model
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = device.name,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (device.isMigratedToCloud) {
                                Surface(
                                    color = Color(0xFF064E3B),
                                    shape = RoundedCornerShape(3.dp)
                                ) {
                                    Text(
                                        text = "MIGRADO",
                                        color = Color(0xFF34D399),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = device.model,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // Revalidar Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF1E293B))
                        .border(1.dp, Color(0xFF334155), RoundedCornerShape(6.dp))
                        .clickable { onRevalidate(device) }
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = AppIcons.Refresh,
                            contentDescription = "Revalidate",
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(11.dp)
                        )
                        Text(
                            text = if (device.isRevalidating) "Checando..." else "Revalidar",
                            color = Color(0xFF38BDF8),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Small set-inform command display below with zero padding
            Text(
                text = "set-inform ${device.currentInformUrl}",
                color = if (device.isMigratedToCloud) Color(0xFF34D399) else TextMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(0.dp)
            )

            // Bottom Info Row: IP and MAC with copy buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // IP Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier.clickable { onCopyIp(device.ip) }
                ) {
                    Text(
                        text = "IP: ",
                        color = TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = device.ip,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = AppIcons.Copy,
                        contentDescription = "Copy IP",
                        tint = TextMuted,
                        modifier = Modifier.size(11.dp)
                    )
                }

                // MAC Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier.clickable { onCopyMac(device.mac) }
                ) {
                    Text(
                        text = "MAC: ",
                        color = TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = device.mac,
                        color = Color(0xFF38BDF8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = AppIcons.Copy,
                        contentDescription = "Copy MAC",
                        tint = TextMuted,
                        modifier = Modifier.size(11.dp)
                    )
                }
            }
        }
    }
}
