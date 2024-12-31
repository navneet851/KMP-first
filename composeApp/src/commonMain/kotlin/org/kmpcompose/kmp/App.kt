package org.kmpcompose.kmp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kmp.composeapp.generated.resources.Res
import kmp.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.kmpcompose.kmp.dependencies.MyViewModel
import org.kmpcompose.kmp.networking.CensorClient
import org.kmpcompose.kmp.util.NetworkError
import org.kmpcompose.kmp.util.onError
import org.kmpcompose.kmp.util.onSuccess
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

data class City(
    val name: String,
    val timeZone: TimeZone
)

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App(client: CensorClient, pref: DataStore<Preferences>) {
    MaterialTheme {
        val factory = rememberPermissionsControllerFactory()
        val controller = remember(factory) {
            factory.createPermissionsController()
        }
        BindEffect(controller)
        val permissionViewModel = viewModel {
            PermissionViewModel(controller)
        }


        val cities = remember {
            listOf(
                City("Paris", TimeZone.of("Europe/Paris")),
                City("London", TimeZone.of("Europe/London")),
                City("Madrid", TimeZone.of("Europe/Madrid")),
//                City("New Delhi", TimeZone.of("Asia/New_Delhi"))
            )
        }
        var cityTimes by remember {
            mutableStateOf(
                listOf<Pair<City, LocalDateTime>>()
            )
        }

        LaunchedEffect(true){
            while (true){
                cityTimes = cities.map {
                    val now = Clock.System.now()
                    it to now.toLocalDateTime(it.timeZone)
                }
                delay(1000)
            }
        }


        val viewModel = koinViewModel<MyViewModel>()
        val navController = rememberNavController()
        NavHost(navController, startDestination = "home") {
            composable("home") {
                var text by remember {
                    mutableStateOf("")
                }
                var result by remember {
                    mutableStateOf<String?>(null)
                }
                var error by remember {
                    mutableStateOf<NetworkError?>(null)
                }
                val scope = rememberCoroutineScope()

                val counter by pref
                    .data
                    .map {
                        val counterKey = intPreferencesKey("counter")
                        it[counterKey] ?: 0
                    }
                    .collectAsState(0)
                Box(
//                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(counter.toString())
                    LazyColumn {
                        items(cityTimes){(city, dateTime) ->
                            Text("City $city")
                            Text(
                                "Time ${dateTime.format(
                                    LocalDateTime.Format { 
                                        hour()
                                        char(':')
                                        minute()
                                        char(':')
                                        second()
                                    }
                                )}"
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (permissionViewModel.state) {
                            PermissionState.Granted -> {
                                Text("audio permission granted")
                            }

                            PermissionState.DeniedAlways -> {
                                Text("audio permission permanently denied")
                                Button(
                                    onClick = {
                                        controller.openAppSettings()
                                    }
                                ) {
                                    Text("open app settings")
                                }
                            }

                            else -> {
                                Button(
                                    onClick = {
                                        permissionViewModel.provideOrRequestAudioPermission()
                                    }
                                ) {
                                    Text("Request Audio Permission")
                                }
                            }
                        }


                        Button(
                            onClick = {
                                scope.launch {
                                    pref.edit { dataStore ->
                                        val counterKey = intPreferencesKey("counter")
                                        dataStore[counterKey] = counter + 1
                                    }
                                }
                            }

                        ) {
                            Text("increment")
                        }
                        Text(
                            text = viewModel.getName()
                        )

                        TextField(
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                scope.launch {
                                    error = null

                                    client.censorWords(text)
                                        .onSuccess {
                                            result = it
                                        }
                                        .onError {
                                            error = it
                                        }
                                }
                            }
                        ) {
                            Text("censor")
                        }

                        result?.let {
                            Text(it)
                        }
                        error?.let {
                            Text(
                                text = it.name,
                                color = Color.Red
                            )
                        }
                    }

                }

            }
        }

    }
}