package com.app.screen.categories

import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.app.a.BaseVM
import com.app.a.delay
import com.app.a.remAssign
import com.app.a.whenDebug
import com.app.repository.CategoriesRepository
import com.app.repository.CategoriesRepositoryImpl
import com.app.ui.expectation.CategoryTab
import java.util.concurrent.TimeUnit

/**
 * Class that provides data to view and handles state of representation for tabs screen
 *
 * @param repository source of data for categories
 */
class CategoriesVM(
    private val repository: CategoriesRepository = CategoriesRepositoryImpl()
                  ) : BaseVM() {

    private val tabsImpl = MutableLiveData<List<CategoryTab>>()
    val tabs: LiveData<List<CategoryTab>> get() = tabsImpl

    @Suppress("unused")
    @OnLifecycleEvent(Event.ON_CREATE)
    private fun retrieveTabsInitially() {
        if (tabsImpl.value == null) execute {
            whenDebug {
                TimeUnit.SECONDS.delay(1)
            }
            tabsImpl %= repository.getCategories().map { CategoryTab(it) }
        }
    }

    fun reloadTabs() =
        retrieveTabsInitially()
}