package com.cieslak.cama.presentation.registration

import com.cieslak.cama.presentation.base.BaseEvent

sealed interface RegistrationEvent: BaseEvent {
    data object RegisterPhone: RegistrationEvent
    data object VerifyCode: RegistrationEvent
    data class UpdatePhoneNumber(val phone: String): RegistrationEvent
    data class UpdateCode(val code: String): RegistrationEvent
}
