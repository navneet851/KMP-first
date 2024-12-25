package org.kmpcompose.kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.kmpcompose.kmp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Kmp",
        ) {
            App()
        }
    }
}