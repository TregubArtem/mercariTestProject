package com.app.screen.timeline

import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.app.global.BaseVM
import com.app.global.delay
import com.app.global.remAssign
import com.app.global.whenDebug
import com.app.api.model.CategoryModel
import com.app.repository.TimelineRepository
import com.app.repository.TimelineRepositoryImpl
import com.app.ui.expectation.TimelineItem
import java.util.concurrent.TimeUnit

/**
 * ViewModel for timeline screen - handle loading of list data
 *
 * @param category key model to know the source of data to be loaded
 * @param repository source of timeline data
 */
class TimelineVM(
    val category: CategoryModel,
    val repository: TimelineRepository = TimelineRepositoryImpl()
                ) : BaseVM() {

    private val itemsImpl = MutableLiveData<List<TimelineItem>>()
    val items: LiveData<List<TimelineItem>> get() = itemsImpl

    private fun retrieveItems() = execute {
        whenDebug {
            TimeUnit.SECONDS.delay(1)
        }
        itemsImpl %= repository.getItems(category).map { TimelineItem(it) }
    }

    @Suppress("unused")
    @OnLifecycleEvent(Event.ON_CREATE)
    private fun retrieveItemsInitially() {
        if (itemsImpl.value == null)
            retrieveItems()
    }

    fun reloadItems() =
        retrieveItems()
}