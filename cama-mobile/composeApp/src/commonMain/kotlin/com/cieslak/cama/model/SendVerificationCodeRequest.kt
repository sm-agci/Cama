package com.cieslak.cama.model

import kotlinx.serialization.Serializable

@Serializable
data class SendVerificationCodeRequest(
    val authenticationId: String,
    val code: String
)