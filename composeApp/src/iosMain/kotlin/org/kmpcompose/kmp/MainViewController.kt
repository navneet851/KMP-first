package org.kmpcompose.kmp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.kmpcompose.kmp.di.initKoin
import org.kmpcompose.kmp.networking.CensorClient
import org.kmpcompose.kmp.networking.createHttpClient

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App(
    client = remember {
        CensorClient(createHttpClient(Darwin.create()))
    }
) }