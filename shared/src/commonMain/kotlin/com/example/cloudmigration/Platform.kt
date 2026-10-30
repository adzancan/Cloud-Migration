package com.example.cloudmigration

data class NetworkInfo(
    val localIp: String,
    val subnet: String
)

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getLocalNetworkInfo(): NetworkInfo
