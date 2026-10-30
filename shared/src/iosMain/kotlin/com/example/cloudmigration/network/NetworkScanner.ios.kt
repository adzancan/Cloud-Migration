package com.example.cloudmigration.network

import com.example.cloudmigration.models.UniFiDevice

actual object NetworkScanner {
    actual suspend fun scanNetwork(
        subnet: String,
        onProgress: (Float) -> Unit
    ): List<UniFiDevice> {
        for (i in 1..10) {
            kotlinx.coroutines.delay(100)
            onProgress(i / 10f)
        }
        return emptyList()
    }
}
