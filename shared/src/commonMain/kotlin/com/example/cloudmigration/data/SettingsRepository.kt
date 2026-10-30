package com.example.cloudmigration.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.cloudmigration.getLocalNetworkInfo

class SettingsRepository(
    initialCloudIp: String = "",
    initialConfigured: Boolean = false,
    private val onSave: (String) -> Unit = {}
) {
    var cloudIpHost by mutableStateOf(initialCloudIp)
    var isCloudConfigured by mutableStateOf(initialConfigured)
    var customSubnet by mutableStateOf("10.0.2.0/24")
    var localIp by mutableStateOf("10.0.2.16")

    init {
        val netInfo = getLocalNetworkInfo()
        localIp = netInfo.localIp
        customSubnet = netInfo.subnet
    }

    fun saveCloudIp(ipOrHost: String) {
        cloudIpHost = ipOrHost.trim()
        isCloudConfigured = cloudIpHost.isNotEmpty()
        onSave(cloudIpHost)
    }

    fun updateSubnet(subnet: String) {
        customSubnet = subnet.trim()
    }
}
