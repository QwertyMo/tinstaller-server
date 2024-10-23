package ru.qwertymo.tinstaller_server

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
class TinstallerServerApplication

fun main(args: Array<String>) {
	runApplication<TinstallerServerApplication>(*args)
}

