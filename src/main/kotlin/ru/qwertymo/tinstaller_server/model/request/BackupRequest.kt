package ru.qwertymo.tinstaller_server.model.request

import org.springframework.web.multipart.MultipartFile

data class BackupRequest (
    var file: MultipartFile?
)