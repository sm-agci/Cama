package com.cieslak.cama.model

import com.cieslak.cama.utils.NetworkError

data class RegistrationState(
    val phoneNumber: String = "",
    val code: String = "",
    val auth: String = "",
    val isPhoneLoading: Boolean = false,
    val isCodeLoading: Boolean = false,
    val error: NetworkError? = null,
)
