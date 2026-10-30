package com.example.cloudmigration.network

object UbiquitiOui {

    val PREFIXES = listOf(
        "00:15:6D",
        "00:27:22",
        "00:50:C2:B0:4",
        "04:18:D6",
        "0C:EA:14",
        "18:E8:29",
        "1C:0B:8B",
        "1C:6A:1B",
        "24:5A:4C",
        "24:A4:3C",
        "28:70:4E",
        "2C:E5:BD",
        "44:D9:E7",
        "58:D6:1F",
        "60:22:32",
        "68:2E:3C",
        "68:72:51",
        "68:D7:9A",
        "6C:63:F8",
        "70:A7:41",
        "74:83:C2",
        "74:AC:B9",
        "74:F9:2C",
        "74:FA:29",
        "78:45:58",
        "78:8A:20",
        "80:2A:A8",
        "84:78:48",
        "8C:30:66",
        "8C:ED:E1",
        "90:41:B2",
        "94:2A:6F",
        "9C:05:D6",
        "A4:F8:FF",
        "A8:9C:6C",
        "AC:8B:A9",
        "B4:FB:E4",
        "CC:35:D9",
        "D0:21:F9",
        "D4:89:C1",
        "D8:B3:70",
        "D8:C2:62",
        "DC:9F:DB",
        "E0:63:DA",
        "E4:38:83",
        "F0:9F:C2",
        "F4:92:BF",
        "F4:E2:C6",
        "FC:EC:DA"
    )

    private val normalizedPrefixes: List<String> = PREFIXES.map { normalizeMac(it) }

    /**
     * Normalizes a MAC address string by converting to uppercase and removing non-hexadecimal chars.
     * Example: "74:83:C2:A1:4B:90" -> "7483C2A14B90"
     */
    fun normalizeMac(mac: String): String {
        return mac.replace(Regex("[^0-9A-Fa-f]"), "").uppercase()
    }

    /**
     * Checks if the given MAC address belongs to Ubiquiti based on the known OUI prefixes.
     */
    fun isUbiquitiMac(mac: String): Boolean {
        val cleanMac = normalizeMac(mac)
        if (cleanMac.isBlank()) return false
        return normalizedPrefixes.any { prefix -> cleanMac.startsWith(prefix) }
    }

    /**
     * Formats MAC address to standard colon-separated format (XX:XX:XX:XX:XX:XX)
     */
    fun formatMac(mac: String): String {
        val clean = normalizeMac(mac)
        if (clean.length < 12) return mac.uppercase()
        return clean.chunked(2).take(6).joinToString(":")
    }
}
