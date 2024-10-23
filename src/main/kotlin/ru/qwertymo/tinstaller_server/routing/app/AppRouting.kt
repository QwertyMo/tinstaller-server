package ru.qwertymo.tinstaller_server.routing.app

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.qwertymo.tinstaller_server.entitiy.AppEntity
import ru.qwertymo.tinstaller_server.model.request.AppRequest
import ru.qwertymo.tinstaller_server.model.response.AppResponse
import ru.qwertymo.tinstaller_server.model.response.RepoResponse
import ru.qwertymo.tinstaller_server.service.AppService
import ru.qwertymo.tinstaller_server.service.RepoService
import ru.qwertymo.tinstaller_server.utils.FileUtils

@RestController
class AppRouting(
    var appService: AppService,
    var repoService: RepoService
) {

    @GetMapping("/repo/{name}")
    fun getRepository(@PathVariable name: String, request: HttpServletRequest): RepoResponse {
        val addr = request.requestURL.split("/repo/")[0]
        return RepoResponse(apps = repoService.getAllApps(name).map {
            AppResponse(it.title, it.description, "$addr/repo/${name}/download/${it.url}", it.appReview, it.category)
        })
    }

    @PostMapping(
        value = ["repo/{name}"],
        consumes = ["multipart/form-data"]
    )
    fun addApp(
        @ModelAttribute app: AppRequest,
        @PathVariable name: String
    ): ResponseEntity<String> {
        if (app.file == null) return ResponseEntity("No file", HttpStatus.BAD_REQUEST)
        if (app.category == null) return ResponseEntity("No category", HttpStatus.BAD_REQUEST)
        if (appService.isAppExists(name, app.title))
            return ResponseEntity("This app already exists", HttpStatus.CONFLICT)
        val filename = FileUtils.addFile(name, app.file!!)
        val repo = repoService.findByName(name)
        appService.save(
            AppEntity(
                title = app.title,
                description = app.description ?: "",
                appReview = app.appReview,
                url = filename,
                repo = repo,
                category = app.category!!
            )
        )
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping(
        value = ["repo/{name}"],
        consumes = ["multipart/form-data"]
    )
    fun removeApp(
        @ModelAttribute app: AppRequest,
        @PathVariable name: String
    ): ResponseEntity<String> {
        val a = appService.getApp(app.title, name) ?: return ResponseEntity(
            "This app doesn't exist",
            HttpStatus.BAD_REQUEST
        )
        appService.remove(a.title, name)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(
        value = ["/repo/{name}/download/{file}"],
        produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE]
    )
    @ResponseBody
    fun downloadFile(
        @PathVariable name: String,
        @PathVariable file: String,
        response: HttpServletResponse
    ): FileSystemResource {
        return FileSystemResource(FileUtils.getFile(name, file))
    }

    @PutMapping(
        value = ["repo/{name}/{app_name}"],
        consumes = ["multipart/form-data"]
    )
    fun updateApp(
        @ModelAttribute app: AppRequest,
        @PathVariable app_name: String,
        @PathVariable name: String
    ): ResponseEntity<String> {
        val a =
            appService.getApp(app_name, name) ?: return ResponseEntity("This app doesn't exist", HttpStatus.BAD_REQUEST)
        if (appService.isAppExists(app.title, name)) return ResponseEntity(
            "Edited app name already exists",
            HttpStatus.CONFLICT
        )
        appService.updateApp(app_name, app, name)
        return ResponseEntity(HttpStatus.OK)
    }
}