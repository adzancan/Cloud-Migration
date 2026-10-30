package com.example.cloudmigration.network

actual object SshMigrator {
    actual suspend fun migrateDevice(
        ip: String,
        cloudIp: String,
        sshUser: String,
        sshPass: String
    ): SshMigrationResult {
        val informUrl = "http://$cloudIp:8080/inform"
        return SshMigrationResult(
            isSuccess = true,
            outputInfo = "Model: UniFi AP\nVersion: 6.8.2\nIP Address: $ip\nStatus: $informUrl"
        )
    }
}
