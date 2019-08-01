package com.app.a

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

/**
 * Base inheritance for any ViewModel class of that project
 */
abstract class BaseVM : ViewModel(), LifecycleObserver {

    private val superJob = SupervisorJob()
    private val scope by lazy { CoroutineScope(Dispatchers.IO + superJob) }
    private val exceptionHandler = CoroutineExceptionHandler { _, e -> onExecuteException(e) }

    private val showProgressImpl = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> get() = showProgressImpl

    protected fun execute(showProgress: Boolean = true, block: suspend () -> Unit) {
        scope.launch(exceptionHandler) {
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

    protected open fun onExecuteException(e: Throwable) {
        toLog(e)
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        superJob.cancelChildren()
    }
}