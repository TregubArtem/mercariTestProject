package com.app.screen.category

import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.app.a.BaseVM
import com.app.a.delay
import com.app.a.remAssign
import com.app.a.whenDebug
import com.app.api.Api
import com.app.ui.expectation.CategoryTab
import java.util.concurrent.TimeUnit

/** Class that provides data to view and handles state of representation for tabs screen */
class CategoryVM : BaseVM() {

    private val tabsImpl = MutableLiveData<List<CategoryTab>>()
    val tabs: LiveData<List<CategoryTab>> get() = tabsImpl

    @Suppress("unused")
    @OnLifecycleEvent(Event.ON_CREATE)
    private fun retrieveTabs() {
        if (tabsImpl.value == null) execute {
            whenDebug {
                TimeUnit.SECONDS.delay(1)
            }
            tabsImpl %= Api.getTabs().map { CategoryTab(it) }
        }
    }
}