package org.kmpcompose.kmp.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.kmpcompose.kmp.util.NetworkError
import org.kmpcompose.kmp.util.Result

class CensorClient(
    private val httpClient : HttpClient
) {
    suspend fun censorWords(unCensoredText: String): Result<String, NetworkError>{
        httpClient.get(
            "https://www.purgomalum.com/service/json"
        ){
            parameter("text", unCensoredText)

        }
    }
}