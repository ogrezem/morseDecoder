package ru.ogrezem.testTornado1.app

import tornadofx.*
import java.io.*

fun main(args: Array<String>) {
    val st = PrintStream(ByteArrayOutputStream())
    System.setErr(st)
    System.setOut(st)
    launch<MyApp>(args)
}