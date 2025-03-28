package ru.qwertymo.tinstaller_server.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.qwertymo.tinstaller_server.entitiy.TokenEntity

@Repository
interface TokenRepository : JpaRepository<TokenEntity, Int>