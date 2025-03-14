package com.cieslak.cama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.arkivanov.decompose.retainedComponent
import com.cieslak.cama.navigation.RootComponent
import com.cieslak.cama.networking.ApiClient
import com.cieslak.cama.networking.createHttpClient
import createDataStore
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = retainedComponent {
            RootComponent(
                componentContext = it,
                prefs = createDataStore(applicationContext),
                client = ApiClient(createHttpClient(OkHttp.create())),
                onFinish = ::onFinish
            )
        }

        setContent {
            App(root)
        }
    }

    private fun onFinish() {
        finishAffinity()
    }
}
