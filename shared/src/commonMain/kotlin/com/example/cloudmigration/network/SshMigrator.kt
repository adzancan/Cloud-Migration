package com.example.cloudmigration.network

data class SshMigrationResult(
    val isSuccess: Boolean,
    val outputInfo: String,
    val errorMessage: String? = null
)

expect object SshMigrator {
    suspend fun migrateDevice(
        ip: String,
        cloudIp: String,
        sshUser: String,
        sshPass: String
    ): SshMigrationResult
}
