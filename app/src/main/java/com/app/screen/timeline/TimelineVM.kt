package com.app.screen.timeline

import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.app.a.BaseVM
import com.app.a.delay
import com.app.a.remAssign
import com.app.a.whenDebug
import com.app.api.Api
import com.app.api.model.CategoryModel
import com.app.ui.expectation.TimelineItem
import java.util.concurrent.TimeUnit

/**
 * ViewModel for timeline screen - handle loading of list data
 */
class TimelineVM(
    val category: CategoryModel
                ) : BaseVM() {

    private val itemsImpl = MutableLiveData<List<TimelineItem>>()
    val items: LiveData<List<TimelineItem>> get() = itemsImpl

    @Suppress("unused")
    @OnLifecycleEvent(Event.ON_CREATE)
    private fun retrieveItems() {
        if (itemsImpl.value == null) execute {
            whenDebug {
                TimeUnit.SECONDS.delay(1)
            }
            itemsImpl %= Api.getList(category.data).map { TimelineItem(it) }
        }
    }

    fun reloadItems() = retrieveItems()
}