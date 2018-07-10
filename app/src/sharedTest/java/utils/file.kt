package utils

import java.nio.charset.Charset

fun readString(any: Any, fileName: String): String? {
    val pathS = any.javaClass.classLoader.getResource(fileName)
    println(pathS)
    val path = pathS?.toURI()
    return pathS.readText(Charset.defaultCharset())
}
