package com.cieslak.cama

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform