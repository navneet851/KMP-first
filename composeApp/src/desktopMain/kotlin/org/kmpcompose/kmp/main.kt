package org.kmpcompose.kmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.engine.okhttp.OkHttp
import org.kmpcompose.kmp.dependencies.DATASTORE_NAME
import org.kmpcompose.kmp.dependencies.createDataStore
import org.kmpcompose.kmp.di.initKoin
import org.kmpcompose.kmp.networking.CensorClient
import org.kmpcompose.kmp.networking.createHttpClient

fun main() {
    val prefs = createDataStore {
        DATASTORE_NAME
    }
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Kmp",
        ) {
            App(
                client = remember {
                    CensorClient(createHttpClient(OkHttp.create()))
                },
                pref = prefs
            )
        }
    }
}