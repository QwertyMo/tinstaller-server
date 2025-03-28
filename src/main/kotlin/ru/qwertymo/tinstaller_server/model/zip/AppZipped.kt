package ru.qwertymo.tinstaller_server.model.zip

import java.io.File

data class AppZipped(
    var title: String,
    var category: String,
    var file: File? = null,
    var description: String? = null,
    var review: String? = null,
    var ext: String = "apk"
)