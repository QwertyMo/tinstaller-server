package ru.qwertymo.tinstaller_server.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.qwertymo.tinstaller_server.service.AppService
import ru.qwertymo.tinstaller_server.service.AuthService
import ru.qwertymo.tinstaller_server.service.RepoService
import ru.qwertymo.tinstaller_server.service.ZippedUtils
import java.io.File

@Service
class InitiateUtils(
    var appService: AppService,
    var repoService: RepoService,
    var authService: AuthService,
    var zippedUtils: ZippedUtils
) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg args: String) {
        if(!authService.isUserExist("admin"))authService.registerUser("admin","password")
    }
}