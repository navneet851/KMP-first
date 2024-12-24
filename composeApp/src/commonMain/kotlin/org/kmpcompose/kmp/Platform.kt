package org.kmpcompose.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform