package org.kmpcompose.kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.ktor.client.engine.okhttp.OkHttp
import org.kmpcompose.kmp.dependencies.createDataStore
import org.kmpcompose.kmp.networking.CensorClient
import org.kmpcompose.kmp.networking.createHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            App(
                client = remember {
                    CensorClient(createHttpClient(OkHttp.create()))
                },
                pref = remember {
                    createDataStore(applicationContext)
                }
            )
        }
    }
}


