package ru.ogrezem.testTornado1.view

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import javafx.stage.Stage
import ru.ogrezem.testTornado1.controller.MorseController
import ru.ogrezem.testTornado1.model.MorseDecoder
import ru.ogrezem.testTornado1.style.Styles
import tornadofx.*

class MainView : View("Дешифратор Морзе") {
    val needToPrintLogs = false
    var morseController = find<MorseController>()
    val inputText = SimpleStringProperty("")
    val labelsWidthProperty = 200.px

    val outputText = SimpleStringProperty("")
    private var bufferProperty = SimpleStringProperty("")
    private var buffer
        set(v) {
            bufferProperty.value = v.toString()
        } get() = bufferProperty.value
    private var bufferChanged = false

    private var handlingIsRunning = false
    private var runHandlingIsRunning = false

    override val root = gridpane {
        style {
            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
            padding = box(50.px)
            gridLinesVisible = false
            alignment = Pos.CENTER
        }

        label("Вывод") {
            try {
                addClass (
                        Styles.headersLabels
                )
            } catch (ex: Exception) {}
            gridpaneConstraints {
                columnRowIndex(0, 0)
            }
        }
        val textLabel = label (outputText) {
            vgrow = Priority.ALWAYS
            gridpaneConstraints {
                columnRowIndex(0, 1)
                marginTop = 5.0
            }
            style {
                fontSize = 14.px
                minHeight = 200.px
                maxWidth = labelsWidthProperty
                minWidth = 200.px
                backgroundColor += Color.YELLOW
                textAlignment = TextAlignment.LEFT
                alignment = Pos.TOP_LEFT
                padding = box(5.px)
                wrapText = true
            }
            shortpress {
                pressHandle(".")
            }
            longpress {
                pressHandle("-")
            }
        }
        label("Ввод") {
            addClass (
                    Styles.headersLabels
            )
            gridpaneConstraints {
                columnRowIndex(0, 2)
                marginTop = 10.0
            }
            style {
                fontSize = 20.px
            }
        }
        scrollpane {
            isFitToWidth = false
            style {
                prefHeight = 100.px
                maxHeight = 100.px
                minWidth = labelsWidthProperty + 10.px
                maxWidth = labelsWidthProperty + 10.px
                padding = box(5.px)
            }
            gridpaneConstraints {
                columnRowIndex(0, 3)
                marginTop = 5.0
            }
            label (inputText) {
                style {
                    maxWidth = labelsWidthProperty
                    wrapText = true
                    fontSize = 14.px
                    vgrow = Priority.ALWAYS
                }
            }
        }
    }
    private fun checkBufferAndDecode() {
        runLater {
            if (MorseDecoder.morseDictionary.containsKey(buffer)) {
                outputText.value += MorseDecoder.morseDictionary[buffer]
            }
            buffer = ""
            inputText.value += " "
            handlingIsRunning = false
        }
    }
    // Нужно перенести обе функции в MorseController или в MorseModel
    private fun pressHandle(signal: String) {
        if (buffer.length < MorseDecoder.MAX_MORSE_CHAR_SIZE)
            buffer += signal
        else {
            checkBufferAndDecode()
            return
        }
        inputText.value += if (!inputText.value.isEmpty() && inputText.value.last() == ' ')
            "| $signal"
        else
            signal
        bufferChanged = true
        if (!handlingIsRunning) {
            handlingIsRunning = true
            //runHandling()
            runHandlingIsRunning = true
            runAsync {
                val millisSleepTime = 1500L
                val sleepingStep = 100L
                var millisNeedToSleep = millisSleepTime
                while (millisNeedToSleep != 0L) {
                    Thread.sleep(sleepingStep)
                    millisNeedToSleep -= sleepingStep
                    if (bufferChanged) {
                        millisNeedToSleep = millisSleepTime
                        bufferChanged = false
                    }
                }
                checkBufferAndDecode()
            }
        }
    }

    override fun onDock() {
        currentStage?.isResizable = false
    }

    override fun onUndock() {

    }
}