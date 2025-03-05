package com.cieslak.cama.model

import kotlinx.serialization.Serializable

@Serializable
data class SendPhoneNumberRequest(val phoneNumber: String)