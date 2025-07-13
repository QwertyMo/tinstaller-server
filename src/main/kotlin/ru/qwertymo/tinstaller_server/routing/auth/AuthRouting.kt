package ru.qwertymo.tinstaller_server.routing.auth

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.qwertymo.tinstaller_server.model.request.UserRequest
import ru.qwertymo.tinstaller_server.service.AuthService

@RestController
class AuthRouting(
    var authService: AuthService
) {
    @PostMapping(
        value = ["auth/login"],
        consumes = ["multipart/form-data"]
    )
    fun login(
        @ModelAttribute user: UserRequest
    ): ResponseEntity<String> {
        if(user.login == null) return ResponseEntity(HttpStatus.UNAUTHORIZED)
        val bearer = authService.authUser(user.login!!, user.password)
        return if(bearer==null) ResponseEntity(HttpStatus.UNAUTHORIZED)
        else ResponseEntity(bearer, HttpStatus.OK)
    }

    @PutMapping(
        value = ["auth/changepass"],
        consumes = ["multipart/form-data"]
    )
    fun updatePassword(
        @RequestHeader("Authorization") bearer: String,
        @ModelAttribute user: UserRequest
    ):ResponseEntity<String>{
        val usr = authService.getUserByToken(bearer.substring(7, bearer.length))
            ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
        authService.changePassword(usr, user.password)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(
        value = ["/auth/me"])
    fun getMe(
        @RequestHeader("Authorization") bearer: String,
    ): ResponseEntity<*> {
        val user = authService.getUserByToken(bearer.substring(7, bearer.length))
        if (user == null)
            return ResponseEntity<String>(HttpStatus.UNAUTHORIZED)
        return ResponseEntity(user, HttpStatus.OK)
    }

}