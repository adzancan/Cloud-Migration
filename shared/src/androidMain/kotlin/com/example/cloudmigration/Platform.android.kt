package com.example.cloudmigration

import android.os.Build
import java.net.NetworkInterface

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getLocalNetworkInfo(): NetworkInfo {
    try {
        val interfaces = NetworkInterface.getNetworkInterfaces()
        while (interfaces.hasMoreElements()) {
            val intf = interfaces.nextElement()
            val addrs = intf.inetAddresses
            while (addrs.hasMoreElements()) {
                val addr = addrs.nextElement()
                if (!addr.isLoopbackAddress && addr is java.net.Inet4Address) {
                    val hostAddress = addr.hostAddress ?: continue
                    val parts = hostAddress.split('.')
                    if (parts.size == 4) {
                        val subnet = "${parts[0]}.${parts[1]}.${parts[2]}.0/24"
                        return NetworkInfo(hostAddress, subnet)
                    }
                }
            }
        }
    } catch (_: Exception) {
    }
    return NetworkInfo("10.0.2.16", "10.0.2.0/24")
}
