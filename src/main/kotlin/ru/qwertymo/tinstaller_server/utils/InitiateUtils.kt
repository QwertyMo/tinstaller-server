package ru.qwertymo.tinstaller_server.utils

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.qwertymo.tinstaller_server.service.AppService
import ru.qwertymo.tinstaller_server.service.RepoService

@Service
class InitiateUtils(
    var appService: AppService,
    var repoService: RepoService
) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg args: String) {

    }
}