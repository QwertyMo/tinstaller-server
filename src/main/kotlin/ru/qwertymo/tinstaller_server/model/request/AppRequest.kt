package ru.qwertymo.tinstaller_server.model.request

import org.springframework.web.multipart.MultipartFile

data class AppRequest(
    var title: String?,
    var description: String?,
    var appReview: String?,
    var file: MultipartFile?,
    var category: String?
)