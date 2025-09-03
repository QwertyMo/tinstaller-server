package ru.qwertymo.tinstaller_server.model.backup

data class BackupRepo(
    val uuid: String,
    val name: String,
    val apps: List<BackupRepoApp>,
    val sorting: String
)