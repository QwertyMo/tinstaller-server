package ru.qwertymo.tinstaller_server.model.response

import com.fasterxml.jackson.annotation.JsonInclude

data class AppResponse(
    var title: String,
    var description: String,
    var url: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var appReview: String?,
    var category: String
)