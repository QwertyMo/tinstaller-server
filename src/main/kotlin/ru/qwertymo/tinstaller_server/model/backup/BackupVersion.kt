package ru.qwertymo.tinstaller_server.model.backup

data class BackupVersion(
    val uuid: String,
    val version: String,
    val date: Long,
    val fileUUID: String,
    val description: String,
    val exception: String
)
