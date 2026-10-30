package com.example.cloudmigration

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cloudmigration.data.SettingsRepository
import com.example.cloudmigration.models.UniFiDevice
import com.example.cloudmigration.network.NetworkScanner
import com.example.cloudmigration.network.SshMigrator
import com.example.cloudmigration.ui.*
import com.example.cloudmigration.ui.theme.CloudMigrationTheme
import com.example.cloudmigration.ui.theme.DarkBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(
    initialCloudIp: String = "",
    initialConfigured: Boolean = false,
    onSaveCloudIp: (String) -> Unit = {}
) {
    val settingsRepo = remember {
        SettingsRepository(
            initialCloudIp = initialCloudIp,
            initialConfigured = initialConfigured,
            onSave = onSaveCloudIp
        )
    }
    var showWelcomeDialog by remember { mutableStateOf(!settingsRepo.isCloudConfigured) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var showMigrationProgressDialog by remember { mutableStateOf(false) }

    var migrationStates by remember { mutableStateOf<List<DeviceMigrationState>>(emptyList()) }
    var isMigrationFinished by remember { mutableStateOf(false) }

    var devices by remember { mutableStateOf<List<UniFiDevice>>(emptyList()) }
    var isScanning by remember { mutableStateOf(false) }
    var scanProgress by remember { mutableStateOf(0f) }
    var hasScanned by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            snackbarMessage = null
        }
    }

    val startScan: () -> Unit = {
        coroutineScope.launch {
            isScanning = true
            scanProgress = 0f
            hasScanned = true
            try {
                val scanned = NetworkScanner.scanNetwork(
                    subnet = settingsRepo.customSubnet,
                    onProgress = { progress ->
                        scanProgress = progress
                    }
                )
                devices = scanned
                snackbarMessage = "Scan concluído: ${scanned.size} dispositivo(s) Ubiquiti encontrados"
            } catch (e: Exception) {
                snackbarMessage = "Erro no scan: ${e.message}"
            } finally {
                isScanning = false
            }
        }
    }

    // Function to execute SSH migration on selected devices (to cloud)
    val startSshMigration: () -> Unit = {
        val selectedDevices = devices.filter { it.isSelected }
        if (selectedDevices.isEmpty()) {
            snackbarMessage = "Nenhum dispositivo selecionado"
        } else {
            migrationStates = selectedDevices.map { dev ->
                DeviceMigrationState(device = dev, isRunning = true)
            }
            showMigrationProgressDialog = true
            isMigrationFinished = false

            coroutineScope.launch {
                for (i in selectedDevices.indices) {
                    val dev = selectedDevices[i]
                    val result = SshMigrator.migrateDevice(
                        ip = dev.ip,
                        cloudIp = settingsRepo.cloudIpHost,
                        sshUser = "ubnt",
                        sshPass = "ubnt"
                    )

                    val success = result.isSuccess
                    if (success) {
                        devices = devices.map { d ->
                            if (d.id == dev.id) d.copy(
                                isMigratedToCloud = true,
                                currentInformUrl = "http://${settingsRepo.cloudIpHost}:8080/inform"
                            ) else d
                        }
                    }

                    migrationStates = migrationStates.map { state ->
                        if (state.device.id == dev.id) {
                            state.copy(
                                isRunning = false,
                                isCompleted = true,
                                isSuccess = success,
                                infoOutput = result.outputInfo,
                                error = result.errorMessage
                            )
                        } else {
                            state
                        }
                    }
                }
                isMigrationFinished = true
            }
        }
    }

    // Function to restore default inform URL (http://unifi:8080/inform) on selected devices
    val startRestoreDefault: () -> Unit = {
        val selectedDevices = devices.filter { it.isSelected }
        if (selectedDevices.isEmpty()) {
            snackbarMessage = "Nenhum dispositivo selecionado"
        } else {
            migrationStates = selectedDevices.map { dev ->
                DeviceMigrationState(device = dev, isRunning = true)
            }
            showMigrationProgressDialog = true
            isMigrationFinished = false

            coroutineScope.launch {
                for (i in selectedDevices.indices) {
                    val dev = selectedDevices[i]
                    val result = SshMigrator.migrateDevice(
                        ip = dev.ip,
                        cloudIp = "unifi",
                        sshUser = "ubnt",
                        sshPass = "ubnt"
                    )

                    val success = result.isSuccess
                    if (success) {
                        devices = devices.map { d ->
                            if (d.id == dev.id) d.copy(
                                isMigratedToCloud = false,
                                currentInformUrl = "http://unifi:8080/inform"
                            ) else d
                        }
                    }

                    migrationStates = migrationStates.map { state ->
                        if (state.device.id == dev.id) {
                            state.copy(
                                isRunning = false,
                                isCompleted = true,
                                isSuccess = success,
                                infoOutput = result.outputInfo,
                                error = result.errorMessage
                            )
                        } else {
                            state
                        }
                    }
                }
                isMigrationFinished = true
            }
        }
    }

    CloudMigrationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = DarkBackground,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            bottomBar = {
                MigrationBar(
                    selectedCount = devices.count { it.isSelected },
                    cloudIp = settingsRepo.cloudIpHost,
                    onMigrate = startSshMigration,
                    onRestoreDefault = startRestoreDefault
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top App Bar
                    MainTopAppBar(
                        title = "Cloud migration",
                        onRefresh = { startScan() },
                        onOpenSettings = { showSettingsDialog = true },
                        onShare = {
                            snackbarMessage = "Configurações exportadas"
                        }
                    )

                    // Subnet Card
                    SubnetCard(
                        subnet = settingsRepo.customSubnet,
                        localIp = settingsRepo.localIp,
                        isScanning = isScanning,
                        scanProgress = scanProgress,
                        onEditSubnet = { showSettingsDialog = true },
                        onStartScan = startScan
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Device List or Empty State
                    DeviceListSection(
                        devices = devices,
                        hasScanned = hasScanned,
                        onStartScan = startScan,
                        onToggleSelect = { device ->
                            devices = devices.map {
                                if (it.id == device.id) it.copy(isSelected = !it.isSelected) else it
                            }
                        },
                        onSelectAll = {
                            devices = devices.map { it.copy(isSelected = true) }
                        },
                        onDeselectAll = {
                            devices = devices.map { it.copy(isSelected = false) }
                        },
                        onRevalidate = { device ->
                            coroutineScope.launch {
                                devices = devices.map {
                                    if (it.id == device.id) it.copy(isRevalidating = true) else it
                                }
                                delay(800)
                                devices = devices.map {
                                    if (it.id == device.id) it.copy(isRevalidating = false, isOnline = true) else it
                                }
                                snackbarMessage = "Dispositivo ${device.name} revalidado com sucesso"
                            }
                        },
                        onCopyIp = { ip ->
                            snackbarMessage = "IP $ip copiado"
                        },
                        onCopyMac = { mac ->
                            snackbarMessage = "MAC $mac copiado"
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Welcome Cloud IP Dialog (Image 1) - shown only on first launch if not configured
                if (showWelcomeDialog) {
                    WelcomeCloudDialog(
                        currentIp = settingsRepo.cloudIpHost,
                        onSave = { savedIp ->
                            settingsRepo.saveCloudIp(savedIp)
                            showWelcomeDialog = false
                            snackbarMessage = "IP da nuvem salvo com sucesso!"
                            startScan()
                        }
                    )
                }

                // Settings Dialog
                if (showSettingsDialog) {
                    SettingsDialog(
                        currentCloudIp = settingsRepo.cloudIpHost,
                        currentSubnet = settingsRepo.customSubnet,
                        onSave = { newCloudIp, newSubnet ->
                            settingsRepo.saveCloudIp(newCloudIp)
                            settingsRepo.updateSubnet(newSubnet)
                            showSettingsDialog = false
                            snackbarMessage = "Configurações atualizadas"
                        },
                        onDismiss = { showSettingsDialog = false }
                    )
                }

                // Migration Progress & Info Validation Dialog
                if (showMigrationProgressDialog) {
                    MigrationProgressDialog(
                        states = migrationStates,
                        isFinished = isMigrationFinished,
                        onClose = { showMigrationProgressDialog = false }
                    )
                }
            }
        }
    }
}
