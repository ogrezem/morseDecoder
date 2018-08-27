package ru.ogrezem.testTornado1.app

import ru.ogrezem.testTornado1.style.Styles
import ru.ogrezem.testTornado1.view.MainView
import tornadofx.App
import java.io.File
import java.util.logging.*
import java.io.FileOutputStream
import java.io.PrintStream



class MyApp: App(MainView::class, Styles::class) {
}