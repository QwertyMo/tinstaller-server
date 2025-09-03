package ru.qwertymo.tinstaller_server.model.backup

data class BackupApp(
    val uuid: String,
    val title: String,
    val description: String,
    val category: List<String>,
    val reviewURL: String,
    val versions: List<BackupVersion>,
    val iconUUID: String
)