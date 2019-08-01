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
 *
 * @param category key model to know the source of data to be loaded
 */
class TimelineVM(
    val category: CategoryModel
                ) : BaseVM() {

    private val itemsImpl = MutableLiveData<List<TimelineItem>>()
    val items: LiveData<List<TimelineItem>> get() = itemsImpl

    private suspend fun retrieveItems() {
        whenDebug {
            TimeUnit.SECONDS.delay(1)
        }
        itemsImpl %= Api.getList(category.data).map { TimelineItem(it) }
    }

    @Suppress("unused")
    @OnLifecycleEvent(Event.ON_CREATE)
    private fun retrieveItemsInitially() {
        if (itemsImpl.value == null) execute {
            retrieveItems()
        }
    }

    fun reloadItems() = execute {
        retrieveItems()
    }
}