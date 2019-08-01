package com.app.api

import com.app.R
import com.app.a.AnyError
import com.app.a.any
import retrofit2.HttpException

/**
 * Method to convert http exception to correct message to user. Should be expanded in the future.
 *
 * @return representation of error that ready to be shown
 */
fun HttpException.toAnyErrors(): AnyError {
    val code = code().toString()
    var message = message()

    if (message.isEmpty())
        message = this.message ?: localizedMessage ?: "Server response $code"

    return codeToAnyError(code, message)
}

/**
 * Method allow to convert code
 *
 * @param code unique string definition of error from server
 * @return representation of error that ready to be shown
 */
private fun codeToAnyError(code: String, message: String): AnyError =
    when (code) {
        "403" -> R.id.error_forbidden any R.string.error_forbidden
        else  -> R.id.error_api any message
    }
