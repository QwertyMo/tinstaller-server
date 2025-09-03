package ru.qwertymo.tinstaller_server.model.backup

data class BackupPackage(
    val uuid: String,
    val date: Long,
    val backupApps: List<BackupApp>,
    val backupRepos: List<BackupRepo>,
)