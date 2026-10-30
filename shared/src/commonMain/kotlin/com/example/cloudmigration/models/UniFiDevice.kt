package com.example.cloudmigration.models

enum class DeviceType {
    GATEWAY,
    SWITCH,
    AP,
    CONTROLLER,
    CAMERA_DOORBELL,
    OTHER
}

data class UniFiDevice(
    val id: String,
    val name: String,
    val model: String,
    val ip: String,
    val mac: String,
    val type: DeviceType,
    val isSelected: Boolean = false,
    val isRevalidating: Boolean = false,
    val isOnline: Boolean = true,
    val isMigratedToCloud: Boolean = false,
    val currentInformUrl: String = "http://unifi:8080/inform",
    val statusMessage: String? = null
)
