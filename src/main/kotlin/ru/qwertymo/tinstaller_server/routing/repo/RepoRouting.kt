package ru.qwertymo.tinstaller_server.routing.repo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import ru.qwertymo.tinstaller_server.entitiy.RepoEntity
import ru.qwertymo.tinstaller_server.service.RepoService

@RestController
class RepoRouting(
    var repoService: RepoService
) {
    @PostMapping("/repo/{name}/create")
    fun createRepository(@PathVariable name:String): ResponseEntity<String> {
        if(repoService.isRepoExists(name))
            return ResponseEntity("This repository already exists", HttpStatus.CONFLICT)
        repoService.save(RepoEntity(name = name))
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping("/repo/{name}/remove")
    fun removeRepository(@PathVariable name:String): ResponseEntity<String> {
        return if(repoService.remove(name)) ResponseEntity(HttpStatus.OK)
        else ResponseEntity("This repository doesn't exist", HttpStatus.CONFLICT)
    }
}