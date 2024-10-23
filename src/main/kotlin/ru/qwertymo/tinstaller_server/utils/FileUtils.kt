package ru.qwertymo.tinstaller_server.utils

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

class FileUtils {
    companion object{
        val dir = System.getProperty("user.dir")

        fun addFile(repo: String, file: MultipartFile):String{
            File("$dir/apps/$repo").let { if(!it.exists())it.mkdirs() }
            val filename = "${UUID.randomUUID()}.${file.originalFilename?.substring(file.originalFilename!!.lastIndexOf(".") + 1)}"
            val output = File("$dir/apps/$repo/$filename")
            file.inputStream.copyTo(output.outputStream())
            return filename
        }

        fun removeFile(repo: String, name: String):Boolean{
            val file = File("$dir/apps/$repo/$name")
            return file.delete()
        }

        fun getFile(repo:String, name:String):File{
            return File("$dir/apps/$repo/$name")
        }
    }
}