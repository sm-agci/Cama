package com.cieslak.cama

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.cieslak.cama.networking.ApiClient
import com.cieslak.cama.utils.NetworkError
import com.cieslak.cama.utils.onError
import com.cieslak.cama.utils.onSuccess
import kotlinx.coroutines.launch

@Composable
@Preview
fun App(client: ApiClient) {
    MaterialTheme {
        var phoneNumber by remember { mutableStateOf("") }
        var code by remember { mutableStateOf("") }
        var auth by remember { mutableStateOf("") }

        var isPhoneLoading by remember { mutableStateOf(false) }
        var isCodeLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<NetworkError?>(null) }

        val scope = rememberCoroutineScope()
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text("Phone number")
                }
            )
            Button(onClick = {
                scope.launch {
                    isPhoneLoading = true
                    errorMessage = null

                    client.sendPhoneNumber(phoneNumber)
                        .onSuccess {
                            auth = it.authenticationId.split("|").last()
                            code = it.authenticationId.split("|").first()
                        }
                        .onError {
                            errorMessage = it
                        }
                    isPhoneLoading = false
                }
            }) {
                if (isPhoneLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(15.dp),
                        strokeWidth = 1.dp,
                        color = Color.White
                    )
                } else {
                    Text("Register Phone number")
                }
            }
            AnimatedVisibility(visible = auth.isEmpty().not()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TextField(
                        value = code,
                        onValueChange = { code = it },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text("Code")
                        }
                    )
                    Button(onClick = {
                        scope.launch {
                            isCodeLoading = true
                            errorMessage = null

                            client.sendValidateCode(auth, code)
                                .onSuccess {

                                }
                                .onError {
                                    errorMessage = it
                                }
                            isCodeLoading = false
                        }
                    }) {
                        if (isCodeLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(15.dp),
                                strokeWidth = 1.dp,
                                color = Color.White
                            )
                        } else {
                            Text("Send Code")
                        }
                    }
                }
            }

            errorMessage?.let {
                Text(
                    text = it.name,
                    color = Color.Red
                )
            }
        }
    }
}
