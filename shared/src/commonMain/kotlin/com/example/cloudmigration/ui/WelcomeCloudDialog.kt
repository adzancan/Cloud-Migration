package com.example.cloudmigration.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cloudmigration.ui.icons.AppIcons
import com.example.cloudmigration.ui.theme.DarkCardBg
import com.example.cloudmigration.ui.theme.DarkCardBorder
import com.example.cloudmigration.ui.theme.PrimaryBlue
import com.example.cloudmigration.ui.theme.TextMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeCloudDialog(
    currentIp: String,
    onSave: (String) -> Unit,
    onDismissRequest: (() -> Unit)? = null
) {
    var inputIp by remember { mutableStateOf(currentIp) }
    var isError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(DarkCardBg)
                .border(1.dp, DarkCardBorder, RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Cloud Icon
                Icon(
                    imageVector = AppIcons.Cloud,
                    contentDescription = "Cloud",
                    tint = PrimaryBlue,
                    modifier = Modifier.size(42.dp)
                )

                // Title
                Text(
                    text = "Welcome to Cloud migration",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                // Subtitle / Description
                Text(
                    text = "To get started, please enter the IP address or host of the cloud server where devices will be sent:",
                    color = TextMuted,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                // Input Field
                OutlinedTextField(
                    value = inputIp,
                    onValueChange = {
                        inputIp = it
                        isError = false
                    },
                    placeholder = {
                        Text("Cloud IP or Host", color = TextMuted)
                    },
                    singleLine = true,
                    isError = isError,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = DarkCardBorder,
                        focusedContainerColor = Color(0xFF0F172A),
                        unfocusedContainerColor = Color(0xFF0F172A),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Helper text
                Text(
                    text = "You can change this IP anytime in the settings menu.",
                    color = TextMuted,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Save button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = {
                            if (inputIp.isBlank()) {
                                isError = true
                            } else {
                                onSave(inputIp)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .width(110.dp)
                            .height(44.dp)
                    ) {
                        Text(
                            text = "Save",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
