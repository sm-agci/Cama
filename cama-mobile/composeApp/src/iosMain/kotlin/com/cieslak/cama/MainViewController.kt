package com.cieslak.cama

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.cieslak.cama.networking.ApiClient
import com.cieslak.cama.networking.createHttpClient
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController {
    App(client = remember { ApiClient(createHttpClient(Darwin.create())) })
}