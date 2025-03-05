package com.cieslak.cama.networking

import com.cieslak.cama.model.SendPhoneNumberRequest
import com.cieslak.cama.model.SendPhoneNumberResponse
import com.cieslak.cama.model.SendVerificationCodeRequest
import com.cieslak.cama.utils.NetworkError
import io.ktor.client.HttpClient
import com.cieslak.cama.utils.Result.Error
import com.cieslak.cama.utils.Result
import com.cieslak.cama.utils.Result.Success
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class ApiClient(
    private val httpClient: HttpClient,
) {

    suspend fun sendPhoneNumber(phoneNumber: String): Result<SendPhoneNumberResponse, NetworkError> {
        val response = try {
            httpClient.post(
                urlString = "https://cama-api.onrender.com/api/v1/cama/otp/send-code"
            ) {
                contentType(ContentType.Application.Json)
                setBody(
                    SendPhoneNumberRequest(phoneNumber = phoneNumber)
                )
            }
        } catch (e: UnresolvedAddressException) {
            return Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            return Error(NetworkError.UNKNOWN)
        }

        return when (response.status.value) {
            in 200..299 -> {
                val body = response.body<SendPhoneNumberResponse>()
                Success(body)
            }

            401 -> Error(NetworkError.UNAUTHORIZED)
            409 -> Error(NetworkError.CONFLICT)
            408 -> Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Error(NetworkError.SERVER_ERROR)
            else -> Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun sendValidateCode(auth: String, code: String): Result<Unit, NetworkError> {
        val response = try {
            httpClient.post(
                urlString = "https://cama-api.onrender.com/api/v1/cama/otp/validate-code"
            ) {
                contentType(ContentType.Application.Json)
                setBody(
                    SendVerificationCodeRequest(authenticationId = auth, code = code)
                )
            }
        } catch (e: UnresolvedAddressException) {
            return Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            return Error(NetworkError.UNKNOWN)
        }

        return when (response.status.value) {
            in 200..299 -> {
                Success(Unit)
            }

            401 -> Error(NetworkError.UNAUTHORIZED)
            409 -> Error(NetworkError.CONFLICT)
            408 -> Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Error(NetworkError.SERVER_ERROR)
            else -> Error(NetworkError.UNKNOWN)
        }
    }
}