package org.kmpcompose.kmp.networking

import kotlinx.serialization.Serializable

@Serializable
data class CensoredText(
    val result : String
)