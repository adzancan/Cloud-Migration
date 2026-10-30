package com.example.cloudmigration.network

import com.example.cloudmigration.models.UniFiDevice

expect object NetworkScanner {
    suspend fun scanNetwork(
        subnet: String,
        onProgress: (Float) -> Unit
    ): List<UniFiDevice>
}
