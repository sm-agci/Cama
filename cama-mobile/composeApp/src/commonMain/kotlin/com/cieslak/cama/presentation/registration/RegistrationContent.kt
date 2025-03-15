package com.cieslak.cama.presentation.registration

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cieslak.cama.model.RegistrationState

@Composable
fun Registration(component: RegistrationComponent) {
    val state by component.contentState.collectAsState()
    RegistrationView(
        viewState = state,
        onPhoneChange = { component.onEvent(RegistrationEvent.UpdatePhoneNumber(it)) },
        onCodeChange = { component.onEvent(RegistrationEvent.UpdateCode(it)) },
        onEvent = { component.onEvent(it) }
    )
}

@Composable
fun RegistrationView(
    viewState: RegistrationState,
    onPhoneChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onEvent: (RegistrationEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        TextField(
            value = viewState.phoneNumber,
            onValueChange = onPhoneChange,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text("Phone number")
            }
        )
        Button(onClick = { onEvent(RegistrationEvent.RegisterPhone) }) {
            if (viewState.isPhoneLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(15.dp),
                    strokeWidth = 1.dp,
                    color = Color.White
                )
            } else {
                Text("Register")
            }
        }
        AnimatedVisibility(visible = viewState.auth.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = viewState.code,
                    onValueChange = onCodeChange,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text("Code")
                    }
                )
                Button(onClick = { onEvent(RegistrationEvent.VerifyCode) }) {
                    if (viewState.isCodeLoading) {
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

        viewState.error?.let {
            Text(
                text = it.name,
                color = Color.Red
            )
        }
    }
}
