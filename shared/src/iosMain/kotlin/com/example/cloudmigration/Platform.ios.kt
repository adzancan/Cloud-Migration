package com.example.cloudmigration

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getLocalNetworkInfo(): NetworkInfo {
    return NetworkInfo("192.168.1.50", "192.168.1.0/24")
}
