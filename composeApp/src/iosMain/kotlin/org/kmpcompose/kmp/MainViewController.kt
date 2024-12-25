package org.kmpcompose.kmp

import androidx.compose.ui.window.ComposeUIViewController
import org.kmpcompose.kmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }