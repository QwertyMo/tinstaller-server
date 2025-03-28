package ru.qwertymo.tinstaller_server.service

import org.springframework.stereotype.Service
import ru.qwertymo.tinstaller_server.entitiy.TokenEntity
import ru.qwertymo.tinstaller_server.entitiy.UserEntity
import ru.qwertymo.tinstaller_server.repository.TokenRepository
import ru.qwertymo.tinstaller_server.repository.UserRepository
import java.security.MessageDigest
import java.util.*

@Service
class AuthService(
    private var userRepository: UserRepository,
    private var tokenRepository: TokenRepository
) {
    fun isUserExist(login: String):Boolean{
        return userRepository.findAll().find { it.name == login }!=null
    }

    fun authUser(login: String, password: String):String?{
        println(hash(login, password))
        val user = userRepository.findAll().find { it.name == login && it.password == hash(login, password) }
        if (user==null) return null
        val token = UUID.randomUUID().toString()
        tokenRepository.save(TokenEntity(user, token))
        return token
    }

    fun getUserByToken(token:String):String?{
        val t = tokenRepository.findAll().find { it.token == token } ?: return null
        val u = userRepository.findAll().find { it.id == t.user?.id } ?: return null
        return u.name
    }

    fun registerUser(login: String, password: String):String?{
        userRepository.save(UserEntity(name = login, password = hash(login,password)))
        return authUser(login,password)
    }


    fun changePassword(login: String, password: String){
        if(!isUserExist(login))return
        val user = userRepository.findAll().find { it.name == login }!!
        user.password = hash(login,password)
        userRepository.save(user)
    }

    fun hash(login: String, password: String): String{
        val d = MessageDigest.getInstance("SHA-256")
        return Base64.getUrlEncoder().encodeToString(
            d.digest("$password$login".toByteArray(Charsets.UTF_8))
        )
    }
}