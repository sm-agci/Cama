package com.cieslak.cama.presentation.registration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.cieslak.cama.model.RegistrationState
import com.cieslak.cama.networking.ApiClient
import com.cieslak.cama.presentation.base.BaseComponent
import com.cieslak.cama.utils.onError
import com.cieslak.cama.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RegistrationComponent(
    componentContext: ComponentContext,
    private val client: ApiClient,
    private val onNavigateToToDo: () -> Unit,
) : BaseComponent<RegistrationEvent, RegistrationState>(), ComponentContext by componentContext {

    private val scope = coroutineScope(Dispatchers.Default + SupervisorJob())

    override val defaultContentState = RegistrationState()

    override fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.UpdatePhoneNumber -> modify { copy(phoneNumber = event.phone) }
            is RegistrationEvent.UpdateCode -> modify { copy(code = event.code) }
            is RegistrationEvent.RegisterPhone -> register()
            is RegistrationEvent.VerifyCode -> verifyCode()
        }
    }

    private fun register() {
        scope.launch {
            modify { copy(isPhoneLoading = true, error = null) }
            client.sendPhoneNumber(contentState.value.phoneNumber)
                .onSuccess {
                    val auth = it.authenticationId.split("|").last()
                    val code = it.authenticationId.split("|").first()
                    modify { copy(auth = auth, code = code, isPhoneLoading = false) }
                }
                .onError {
                    modify { copy(error = it, isPhoneLoading = false) }
                }
        }
    }

    private fun verifyCode() {
        scope.launch {
            modify { copy(isCodeLoading = true, error = null) }
            val auth = contentState.value.auth
            val code = contentState.value.code
            client.sendValidateCode(auth, code)
                .onSuccess { onNavigateToToDo() }
                .onError { modify { copy(error = it, isCodeLoading = false) } }
        }
    }
}
