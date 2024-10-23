package ru.qwertymo.tinstaller_server.service

import org.springframework.stereotype.Service
import ru.qwertymo.tinstaller_server.entitiy.AppEntity
import ru.qwertymo.tinstaller_server.entitiy.RepoEntity
import ru.qwertymo.tinstaller_server.repository.RepoRepository
import kotlin.jvm.optionals.getOrNull

@Service
class RepoService(
    private var appService: AppService,
    private var repoRepository: RepoRepository
) {
    fun save(value: RepoEntity){
        repoRepository.save(value)
    }

    fun isRepoExists(name: String):Boolean{
        return repoRepository.findAll().find { it.name == name } != null
    }

    fun remove(name:String):Boolean{
        val repo = findByName(name) ?: return false
        getAllApps(name).forEach { appService.remove(it.title,name) }
        repoRepository.delete(repo)
        return true
    }

    fun getAll():List<RepoEntity>{
        return repoRepository.findAll()
    }

    fun findById(id: Int):RepoEntity?{
        return repoRepository.findById(id).getOrNull()
    }

    fun findByName(name: String): RepoEntity?{
        return repoRepository
            .findAll()
            .find { it.name == name }
    }

    fun getAllApps(id: Int): List<AppEntity>{
        return repoRepository.findById(id).getOrNull()?.apps ?: emptyList()
    }

    fun getAllApps(name: String): List<AppEntity>{
        return repoRepository
            .findAll()
            .find { it.name == name }
            .let { it?.apps ?: emptyList() }
    }
}