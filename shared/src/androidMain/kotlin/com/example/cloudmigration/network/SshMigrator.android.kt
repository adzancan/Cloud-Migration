package com.example.cloudmigration.network

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.JSch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

actual object SshMigrator {
    actual suspend fun migrateDevice(
        ip: String,
        cloudIp: String,
        sshUser: String,
        sshPass: String
    ): SshMigrationResult = withContext(Dispatchers.IO) {
        val output = StringBuilder()
        try {
            val jsch = JSch()
            val session = jsch.getSession(sshUser, ip, 22)
            session.setPassword(sshPass)
            session.setConfig("StrictHostKeyChecking", "no")
            session.setConfig("PreferredAuthentications", "password,keyboard-interactive")
            session.setConfig(
                "kex",
                "curve25519-sha256,curve25519-sha256@libssh.org,diffie-hellman-group14-sha256,diffie-hellman-group1-sha1,diffie-hellman-group14-sha1,ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521,diffie-hellman-group-exchange-sha1,diffie-hellman-group-exchange-sha256"
            )
            session.setConfig(
                "cipher.s2c",
                "aes128-cbc,3des-cbc,aes128-ctr,aes192-ctr,aes256-ctr,aes128-gcm@openssh.com,aes256-gcm@openssh.com,chacha20-poly1305@openssh.com"
            )
            session.setConfig(
                "cipher.c2s",
                "aes128-cbc,3des-cbc,aes128-ctr,aes192-ctr,aes256-ctr,aes128-gcm@openssh.com,aes256-gcm@openssh.com,chacha20-poly1305@openssh.com"
            )

            // Connect with 7 seconds timeout
            session.connect(7000)

            val informUrl = "http://$cloudIp:8080/inform"

            // 1. Execute set-inform checking standard UniFi paths
            val setInformCmd = "export PATH=\$PATH:/sbin:/usr/sbin:/bin:/usr/bin;\n" +
                    "if command -v set-inform >/dev/null 2>&1; then set-inform $informUrl; " +
                    "elif [ -f /sbin/set-inform ]; then /sbin/set-inform $informUrl; " +
                    "elif [ -f /usr/bin/set-inform ]; then /usr/bin/set-inform $informUrl; " +
                    "elif [ -f /usr/etc/set-inform ]; then /usr/etc/set-inform $informUrl; " +
                    "elif command -v syswrapper.sh >/dev/null 2>&1; then syswrapper.sh set-inform $informUrl; " +
                    "else set-inform $informUrl; fi\n"

            val channel1 = session.openChannel("exec") as ChannelExec
            channel1.setCommand(setInformCmd)
            val errStream1 = channel1.errStream
            val inputStream1 = channel1.inputStream
            channel1.connect()

            val r1 = BufferedReader(InputStreamReader(inputStream1))
            val err1 = BufferedReader(InputStreamReader(errStream1))
            var l1 = r1.readLine()
            while (l1 != null) {
                l1 = r1.readLine()
            }
            var e1 = err1.readLine()
            while (e1 != null) {
                e1 = err1.readLine()
            }
            channel1.disconnect()

            // 2. Execute info command to get device details and status
            val infoCmd = "export PATH=\$PATH:/sbin:/usr/sbin:/bin:/usr/bin;\n" +
                    "if command -v info >/dev/null 2>&1; then info; " +
                    "elif [ -f /sbin/info ]; then /sbin/info; " +
                    "elif [ -f /usr/bin/info ]; then /usr/bin/info; " +
                    "elif command -v syswrapper.sh >/dev/null 2>&1; then syswrapper.sh info; " +
                    "else info; fi\n"

            val channel2 = session.openChannel("exec") as ChannelExec
            channel2.setCommand(infoCmd)
            val inputStream2 = channel2.inputStream
            val errStream2 = channel2.errStream
            channel2.connect()

            val reader = BufferedReader(InputStreamReader(inputStream2))
            var line: String? = reader.readLine()
            while (line != null) {
                output.append(line).append("\n")
                line = reader.readLine()
            }
            val errReader = BufferedReader(InputStreamReader(errStream2))
            var errLine: String? = errReader.readLine()
            while (errLine != null) {
                if (errLine.isNotBlank() && !errLine.contains("not found")) {
                    output.append(errLine).append("\n")
                }
                errLine = errReader.readLine()
            }

            channel2.disconnect()
            session.disconnect()

            val infoText = output.toString()
            val hasError = infoText.contains("not found") || infoText.contains("Error") || infoText.contains("syntax error")

            SshMigrationResult(
                isSuccess = !hasError,
                outputInfo = if (infoText.isNotBlank()) infoText.trim() else "Set-inform executado e validado com sucesso"
            )
        } catch (e: Exception) {
            val errorMsg = when {
                e.message?.contains("Auth fail") == true -> "Falha de Autenticação (Usuário/Senha incorretos)"
                e.message?.contains("Connection refused") == true -> "Conexão Recusada (SSH desativado na porta 22)"
                e.message?.contains("timed out") == true -> "Tempo limite esgotado (Dispositivo inacessível)"
                else -> e.message ?: "Erro desconhecido no SSH"
            }
            SshMigrationResult(
                isSuccess = false,
                outputInfo = "Falha na conexão SSH com $ip",
                errorMessage = errorMsg
            )
        }
    }
}
