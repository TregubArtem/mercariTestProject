package com.app.global

import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.app.R

/**
 * Class that used to visualize errors that could happen anywhere
 *
 * @param type should be unique id stored in [values/ids.xml]
 * @param messageRes used to show localized message to user
 * @param message should be used only when [messageRes] is not available
 */
data class AnyError(
    @IdRes val type: Int,
    private @StringRes val messageRes: Int = R.string.error_unknown,
    private val message: String = ""
                   ) {

    fun getString(context: Context): String =
        when {
            messageRes != R.string.error_unknown -> context.getString(messageRes)
            message.isNotEmpty()                 -> message

            else                                 -> context.getString(messageRes)
        }
}

infix fun Int.any(@StringRes messageRes: Int): AnyError = AnyError(this, messageRes = messageRes)
infix fun Int.any(message: String): AnyError = AnyError(this, message = message)