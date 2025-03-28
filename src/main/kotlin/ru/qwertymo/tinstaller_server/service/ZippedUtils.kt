package ru.qwertymo.tinstaller_server.service

import jakarta.transaction.Transactional
import kotlinx.coroutines.coroutineScope
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.qwertymo.tinstaller_server.entitiy.AppEntity
import ru.qwertymo.tinstaller_server.entitiy.RepoEntity
import ru.qwertymo.tinstaller_server.model.zip.AppZipped
import ru.qwertymo.tinstaller_server.utils.FileUtils
import ru.qwertymo.tinstaller_server.utils.FileUtils.Companion
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream


@Service
@Transactional
class ZippedUtils(
    val appService: AppService,
    val repoService: RepoService
) {

    val dir = System.getProperty("user.dir")


    fun loadFromZip(repo: String, mfile: MultipartFile){
        File("$dir/temp/").let { if(!it.exists())it.mkdirs() }
        val file = File("$dir/temp/${UUID.randomUUID()}.temp")
        mfile.inputStream.copyTo(file.outputStream())
        if(repoService.isRepoExists(repo)) return

        val apps: MutableList<AppZipped>  = mutableListOf()
        ZipFile(file).use { zipFile ->
            val entries: Enumeration<out ZipEntry> = zipFile.entries()
            while (entries.hasMoreElements()) {
                val entry = entries.nextElement()
                // Check if entry is a directory
                if (!entry.isDirectory) {
                    zipFile.getInputStream(entry).use { inputStream ->
                        try {
                            val category = entry.name.split("/")[0]
                            val name = entry.name.split("/")[1]
                            var isApk = false
                            var isDesc = false
                            var isReview = false
                            when (entry.name.split("/")[2].split(".")[0]) {
                                "description" -> isDesc = true
                                "app" -> isApk = true
                                "url" -> isReview = true
                            }
                            val app = apps.find { it.title == name } ?: AppZipped(title = name, category = category)
                            if(apps.find { it.title == name }==null) apps.add(app)

                            if(isApk){
                                app.ext = entry.name.split("/")[2].split(".")[1]
                                app.file = File("$dir/temp/${UUID.randomUUID()}.${app.ext}")
                                app.file!!.outputStream().use { output ->
                                    inputStream.copyTo(output)
                                }
                            }
                            if(isDesc) {
                                val reader = BufferedReader(inputStream.reader())
                                reader.use { reader ->
                                    app.description = reader.readText()
                                }
                            }
                            if(isReview){
                                    val reader = BufferedReader(inputStream.reader())
                                    reader.use { reader ->
                                        app.review = reader.readText()
                                    }
                                }

                        }catch (_: Exception){}
                    }
                }
            }
        }
        apps.removeIf { it.file == null || it.description == null }


        repoService.save(RepoEntity(name = repo))
        val repos = repoService.findByName(repo)
        apps.forEach{
            val file = FileUtils.addFile(repo, it.file!!.inputStream(), it.ext)

            appService.save(AppEntity(
                title = it.title,
                description = it.description!!,
                url = file,
                category = it.category,
                appReview = it.review,
                repo = repos
            ))
        }
    }

    @Async
    suspend fun saveToZip(repository: String):String {
        File("${dir}/backup/$repository").let { if (!it.exists()) it.mkdirs() }
        val zip = File("${dir}/backup/$repository/${UUID.randomUUID()}.zip")
        coroutineScope {
            val repo = repoService.getAllApps(repository)
            ZipOutputStream(FileOutputStream(zip)).use { out ->
                repo.forEach { app->
                    val apk = File("$dir/apps/${repository}/${app.url}")
                    FileInputStream(apk).use { input ->
                        val zipEntry = ZipEntry("${app.title}/app${app.url.substring(app.url.lastIndexOf("."))}")
                        out.putNextEntry(zipEntry)
                        input.copyTo(out)
                        out.closeEntry()
                        val desc = ZipEntry("${app.title}/description.txt")
                        out.putNextEntry(desc)
                        app.description.byteInputStream().copyTo(out)
                        out.closeEntry()
                        if(app.appReview!=null){
                            val rev = ZipEntry("${app.title}/url.txt")
                            out.putNextEntry(desc)
                            app.url.byteInputStream().copyTo(out)
                            out.closeEntry()
                        }
                    }
                }
            }
        }

        return zip.name
    }
}