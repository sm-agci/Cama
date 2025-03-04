package com.cieslak.cama.model

import kotlinx.serialization.Serializable

@Serializable
data class SendPhoneNumberResponse(
    val authenticationId: String,
)
