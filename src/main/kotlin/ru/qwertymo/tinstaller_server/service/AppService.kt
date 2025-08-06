package ru.qwertymo.tinstaller_server.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.qwertymo.tinstaller_server.entitiy.AppEntity
import ru.qwertymo.tinstaller_server.model.request.AppRequest
import ru.qwertymo.tinstaller_server.repository.AppRepository
import ru.qwertymo.tinstaller_server.utils.FileUtils

@Service
class AppService(private var appRepository: AppRepository){

    fun save(value: AppEntity){
        appRepository.save(value)
    }

    fun getAll():List<AppEntity>{
        return appRepository.findAll()
    }

    fun isAppExists(repo: String, name: String):Boolean{
        return appRepository.findAll().find { it.repo?.name == repo && it.title == name } != null
    }

    fun remove(name: String, repo:String):Boolean{
        val app = getAll().find { it.repo?.name == repo && it.title == name } ?: return false
        FileUtils.removeFile(repo,app.url)
        appRepository.delete(app)
        return true
    }

    fun getApp(name: String, repo: String):AppEntity?{
        return appRepository.findAll().find { it.repo?.name == repo && it.title == name }
    }

    fun updateApp(name: String, request: AppRequest, repo: String){
        val app = appRepository.findAll().find { it.repo?.name == repo && it.title == name } ?: return
        if(request.title!=null) app.title = request.title!!
        if(request.file!=null){
            FileUtils.removeFile(repo, app.url)
            app.url = FileUtils.addFile(repo, request.file!!)
        }
        if(request.category!=null) app.category = request.category!!
        if(request.appReview!=null) app.appReview = request.appReview
        if(request.description!=null) app.description = request.description!!
        appRepository.save(app)
    }
}