package com.cieslak.cama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.cieslak.cama.networking.ApiClient
import com.cieslak.cama.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                client = remember { ApiClient(createHttpClient(OkHttp.create())) }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(ApiClient(createHttpClient(OkHttp.create())))
}