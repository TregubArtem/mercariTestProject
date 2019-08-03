@file:Suppress("MemberVisibilityCanBePrivate")

package com.app.global

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.R
import com.app.api.toAnyErrors
import com.app.utility.SingleLiveData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/** Base inheritance for any ViewModel class of that project */
abstract class BaseVM : ViewModel(), LifecycleObserver {

    private val exceptionHandler = CoroutineExceptionHandler { _, e -> onExecuteException(e) }

    private val showProgressImpl = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> get() = showProgressImpl

    private val errorMessageImpl = SingleLiveData<AnyError>()
    val errorMessage: LiveData<AnyError> get() = errorMessageImpl

    /**
     * Used to execute something at IO thread
     *
     * @param showProgress flag to indicate that something is doing
     * @param block anything that should be called with suspend
     */
    protected fun execute(showProgress: Boolean = true, block: suspend () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            if (showProgress)
                showProgressImpl %= true

            try {
                block()

            } finally {
                if (showProgress)
                    showProgressImpl %= false
            }
        }
    }

    /**
     * Allow to customize handling of global errors here
     *
     * @param e is the error that should be handled
     */
    protected open fun onExecuteException(e: Throwable) {
        if (e !is CancellationException)
            toLog(e)

        when (e) {
            is CancellationException  -> Unit
            is HttpException          -> postError(e.toAnyErrors())
            is SocketTimeoutException -> postError(R.id.error_timeout any R.string.error_timeout)
            is UnknownHostException   -> postError(R.id.error_unknown_host any R.string.error_unknown_host)

            else                      -> postError(R.id.error_unknown any (e.message ?: e::class.java.name))
        }
    }

    /**
     * Use that when you want to show user some error message
     *
     * @param error entity with main properties to show
     */
    protected fun postError(error: AnyError) {
        errorMessageImpl %= error
    }
}