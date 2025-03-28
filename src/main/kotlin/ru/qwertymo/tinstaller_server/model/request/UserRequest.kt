package ru.qwertymo.tinstaller_server.model.request

data class UserRequest(
    var login: String?,
    var password: String
)