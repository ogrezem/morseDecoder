package ru.ogrezem.testTornado1.style

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream

class Styles : Stylesheet() {
    companion object {
        val headersLabels by cssclass()
    }
    init {

        headersLabels and label {
            fontSize = 20.px
        }
    }
}