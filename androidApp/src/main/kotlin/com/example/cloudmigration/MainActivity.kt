package com.example.cloudmigration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("cloud_migration_prefs", MODE_PRIVATE)
        val savedIp = prefs.getString("cloud_ip", "") ?: ""
        val isConfigured = prefs.getBoolean("is_configured", false)

        setContent {
            App(
                initialCloudIp = savedIp,
                initialConfigured = isConfigured,
                onSaveCloudIp = { ip ->
                    prefs.edit()
                        .putString("cloud_ip", ip)
                        .putBoolean("is_configured", true)
                        .apply()
                }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
