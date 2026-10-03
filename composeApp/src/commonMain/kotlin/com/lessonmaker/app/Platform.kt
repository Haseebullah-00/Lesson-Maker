package com.lessonmaker.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform