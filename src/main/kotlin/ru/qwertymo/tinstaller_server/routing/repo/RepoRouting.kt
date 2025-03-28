package ru.qwertymo.tinstaller_server.routing.repo

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.*
import ru.qwertymo.tinstaller_server.entitiy.RepoEntity
import ru.qwertymo.tinstaller_server.model.request.AppRequest
import ru.qwertymo.tinstaller_server.model.request.BackupRequest
import ru.qwertymo.tinstaller_server.service.AuthService
import ru.qwertymo.tinstaller_server.service.RepoService
import ru.qwertymo.tinstaller_server.service.ZippedUtils
import ru.qwertymo.tinstaller_server.utils.FileUtils

@RestController
class RepoRouting(
    var repoService: RepoService,
    var authService: AuthService,
    var zippedUtils: ZippedUtils
) {
    @PostMapping("/repo/{name}/create")
    fun createRepository(
        @RequestHeader("Authorization") bearer: String,
        @PathVariable name: String
    ): ResponseEntity<String> {
        if (authService.getUserByToken(bearer.substring(7, bearer.length)) == null)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (repoService.isRepoExists(name))
            return ResponseEntity("This repository already exists", HttpStatus.CONFLICT)
        repoService.save(RepoEntity(name = name))
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/repo/{name}/remove")
    fun removeRepository(
        @RequestHeader("Authorization") bearer: String,
        @PathVariable name: String
    ): ResponseEntity<String> {
        if (authService.getUserByToken(bearer.substring(7, bearer.length)) == null)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        return if (repoService.remove(name)) ResponseEntity(HttpStatus.OK)
        else ResponseEntity("This repository doesn't exist", HttpStatus.CONFLICT)
    }

    @PostMapping("/repo/{name}/load")
    fun loadBackup(
        @RequestHeader("Authorization") bearer: String,
        @PathVariable name: String,
        @ModelAttribute app: BackupRequest
    ): ResponseEntity<String> {
        if (authService.getUserByToken(bearer.substring(7, bearer.length)) == null)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        if (app.file == null) ResponseEntity("No file", HttpStatus.BAD_REQUEST)
        zippedUtils.loadFromZip(name, app.file!!)
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping(
        value = ["repo/{name}/backup"],
    )
    suspend fun saveBackup(
        @RequestHeader("Authorization") bearer: String,
        @PathVariable name: String
    ): ResponseEntity<String>{
        if (authService.getUserByToken(bearer.substring(7, bearer.length)) == null)
            return ResponseEntity<String>(HttpStatus.UNAUTHORIZED)
        return ResponseEntity(zippedUtils.saveToZip(name), HttpStatus.OK)

    }

    @GetMapping(
        value = ["/repo/{name}/backup"],
        produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun getBackup(
        @RequestHeader("Authorization") bearer: String,
        @PathVariable name: String
    ): ResponseEntity<*> {
        if (authService.getUserByToken(bearer.substring(7, bearer.length)) == null)
            return ResponseEntity<String>(HttpStatus.UNAUTHORIZED)
        return ResponseEntity("", HttpStatus.OK)
    }
}