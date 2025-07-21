package org.example.poke

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform