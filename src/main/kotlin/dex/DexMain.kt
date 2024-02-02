package dex

import okio.FileSystem
import okio.Path.Companion.toPath
import java.io.PrintWriter

const val path = "classes4.dex"
const val out = "out.log"

fun main() {

    val pathUrl =   DexFile::class.java.classLoader.getResource(path)
    val outUrl  =   DexFile::class.java.classLoader.getResource(out)
    println("url ${pathUrl} outUrl ${outUrl}")
    FileSystem.SYSTEM.openReadOnly(pathUrl.path.toPath()).use {
        DexFile(PrintWriter(outUrl.file), it)
    }
}