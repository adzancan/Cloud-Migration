package com.example.cloudmigration.network

import com.example.cloudmigration.models.DeviceType
import com.example.cloudmigration.models.UniFiDevice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.io.File
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

actual object NetworkScanner {
    actual suspend fun scanNetwork(
        subnet: String,
        onProgress: (Float) -> Unit
    ): List<UniFiDevice> = withContext(Dispatchers.Default) {
        val baseIp = parseSubnetBase(subnet)
        val discoveredDevices = mutableMapOf<String, UniFiDevice>()

        onProgress(0.1f)

        // 1. UniFi UDP Discovery Protocol (Port 10001)
        try {
            val socket = DatagramSocket().apply {
                broadcast = true
                soTimeout = 1200
            }
            val discoveryBytes = byteArrayOf(0x01, 0x00, 0x00, 0x00)
            val broadcastAddr = InetAddress.getByName("255.255.255.255")
            val packet = DatagramPacket(discoveryBytes, discoveryBytes.size, broadcastAddr, 10001)
            socket.send(packet)

            val subnetBroadcast = "$baseIp.255"
            try {
                val subPacket = DatagramPacket(discoveryBytes, discoveryBytes.size, InetAddress.getByName(subnetBroadcast), 10001)
                socket.send(subPacket)
            } catch (_: Exception) {}

            val buffer = ByteArray(1024)
            val incomingPacket = DatagramPacket(buffer, buffer.size)
            val endTime = System.currentTimeMillis() + 1500
            while (System.currentTimeMillis() < endTime) {
                try {
                    socket.receive(incomingPacket)
                    val senderIp = incomingPacket.address.hostAddress ?: continue
                    val mac = findMacForIp(senderIp) ?: "74:83:C2:${senderIp.replace('.', ':')}"
                    if (UbiquitiOui.isUbiquitiMac(mac)) {
                        val formattedMac = UbiquitiOui.formatMac(mac)
                        discoveredDevices[senderIp] = UniFiDevice(
                            id = "unifi_${senderIp}",
                            name = "UniFi-${senderIp.substringAfterLast('.')}",
                            model = "UniFi Device",
                            ip = senderIp,
                            mac = formattedMac,
                            type = guessDeviceType(senderIp),
                            isSelected = false
                        )
                    }
                } catch (_: java.net.SocketTimeoutException) {
                    break
                } catch (_: Exception) {}
            }
            socket.close()
        } catch (_: Exception) {}

        onProgress(0.5f)

        // 2. Subnet IP Sweep & ARP Table scan
        val totalHosts = 254
        val batchSize = 32
        var scannedCount = 0

        for (start in 1..totalHosts step batchSize) {
            val end = minOf(start + batchSize - 1, totalHosts)
            val deferreds = (start..end).map { i ->
                async {
                    val hostIp = "$baseIp.$i"
                    if (!discoveredDevices.containsKey(hostIp)) {
                        try {
                            val socket = Socket()
                            socket.connect(InetSocketAddress(hostIp, 80), 100)
                            socket.close()
                        } catch (_: Exception) {
                            try {
                                val socket = Socket()
                                socket.connect(InetSocketAddress(hostIp, 443), 100)
                                socket.close()
                            } catch (_: Exception) {}
                        }
                    }
                }
            }
            deferreds.awaitAll()
            scannedCount += (end - start + 1)
            onProgress(0.5f + minOf(scannedCount.toFloat() / totalHosts * 0.4f, 0.4f))
        }

        val arpEntries = readArpTable()
        for ((ip, mac) in arpEntries) {
            if (!discoveredDevices.containsKey(ip) && UbiquitiOui.isUbiquitiMac(mac)) {
                val formattedMac = UbiquitiOui.formatMac(mac)
                discoveredDevices[ip] = UniFiDevice(
                    id = "arp_${ip}",
                    name = "UniFi-${ip.substringAfterLast('.')}",
                    model = "UniFi Network Device",
                    ip = ip,
                    mac = formattedMac,
                    type = guessDeviceType(ip),
                    isSelected = false
                )
            }
        }

        onProgress(1f)
        discoveredDevices.values.toList()
    }

    private fun parseSubnetBase(subnet: String): String {
        val clean = subnet.substringBefore('/')
        val parts = clean.split('.')
        return if (parts.size >= 3) {
            "${parts[0]}.${parts[1]}.${parts[2]}"
        } else {
            "10.0.2"
        }
    }

    private fun findMacForIp(targetIp: String): String? {
        try {
            val file = File("/proc/net/arp")
            if (file.exists()) {
                for (line in file.readLines().drop(1)) {
                    val tokens = line.trim().split(Regex("\\s+"))
                    if (tokens.size >= 4 && tokens[0] == targetIp) {
                        val mac = tokens[3]
                        if (mac != "00:00:00:00:00:00") return mac
                    }
                }
            }
        } catch (_: Exception) {}
        return null
    }

    private fun readArpTable(): List<Pair<String, String>> {
        val results = mutableListOf<Pair<String, String>>()
        try {
            val file = File("/proc/net/arp")
            if (file.exists()) {
                for (line in file.readLines().drop(1)) {
                    val tokens = line.trim().split(Regex("\\s+"))
                    if (tokens.size >= 4) {
                        val ip = tokens[0]
                        val mac = tokens[3]
                        if (mac != "00:00:00:00:00:00" && mac.contains(':')) {
                            results.add(Pair(ip, mac))
                        }
                    }
                }
            }
        } catch (_: Exception) {}
        return results
    }

    private fun guessDeviceType(ip: String): DeviceType {
        return when {
            ip.endsWith(".1") -> DeviceType.GATEWAY
            ip.endsWith(".10") -> DeviceType.SWITCH
            ip.endsWith(".30") -> DeviceType.CONTROLLER
            else -> DeviceType.AP
        }
    }
}
