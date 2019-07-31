package com.app.screen.timelineTabs

import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.app.a.delay
import com.app.a.remAssign
import com.app.api.Api
import com.app.ui.expectation.TimelineTab
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Class that provides data to view and handles state of representation for tabs screen
 */
class TimelineTabsViewModel : ViewModel(), LifecycleObserver {

    private val superJob = SupervisorJob()
    private val scope by lazy { CoroutineScope(Dispatchers.IO + superJob) }
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
    }

    private val showProgressImpl = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> get() = showProgressImpl

    private val tabsImpl = MutableLiveData<List<TimelineTab>>()
    val tabs: LiveData<List<TimelineTab>> get() = tabsImpl

    @Suppress("unused")
    @OnLifecycleEvent(Event.ON_CREATE)
    private fun retrieveTabs() {
        if (tabsImpl.value == null) execute {
            TimeUnit.SECONDS.delay(1)

            tabsImpl %= Api.getTabs().map { TimelineTab(it) }
        }
    }

    private fun execute(showProgress: Boolean = true, block: suspend () -> Unit) {
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

    override fun onCleared() {
        super.onCleared()
        superJob.cancelChildren()
    }
}