package com.example.cloudmigration.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF090E17)
val DarkCardBg = Color(0xFF111827)
val DarkCardBorder = Color(0xFF1E293B)
val PrimaryBlue = Color(0xFF2563EB)
val SecondaryBlue = Color(0xFF1D4ED8)
val AccentBlue = Color(0xFF3B82F6)
val TextWhite = Color(0xFFFFFFFF)
val TextMuted = Color(0xFF94A3B8)
val SuccessGreen = Color(0xFF10B981)
val DeviceCardBg = Color(0xFF0F172A)
val DeviceCardBorder = Color(0xFF1E293B)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryBlue,
    background = DarkBackground,
    surface = DarkCardBg,
    onPrimary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite,
    outline = DarkCardBorder
)

@Composable
fun CloudMigrationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
