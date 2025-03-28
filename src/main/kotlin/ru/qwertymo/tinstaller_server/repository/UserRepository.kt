package ru.qwertymo.tinstaller_server.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.qwertymo.tinstaller_server.entitiy.UserEntity

@Repository
interface UserRepository : JpaRepository<UserEntity, Int>