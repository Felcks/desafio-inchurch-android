package br.com.mobfiq.search.core

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

@Throws(IOException::class)
fun readJsonFile(filename: String): String? {
    val br = BufferedReader(InputStreamReader(FileInputStream("../app/src/test/java/matheusfelipe/assets/$filename")))
    val sb = StringBuilder()
    var line: String? = br.readLine()
    while (line != null) {
        sb.append(line)
        line = br.readLine()
    }
    return sb.toString()
}