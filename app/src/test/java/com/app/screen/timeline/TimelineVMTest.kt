package com.app.screen.timeline

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.api.model.CategoryModel
import com.app.api.model.TimelineItemModel
import com.app.api.model.TimelineItemStatus
import com.app.repository.TimelineRepository
import com.app.utility.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/** Check key behaviour correctness work */
@ExperimentalCoroutinesApi
class TimelineVMTest {

    private val category by lazy { CategoryModel("name0", "data0") }

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Test
    fun retrieveItemsInitially() {
        val vm = TimelineVM(category, TimelinekRepositoryImpl)

        coroutineRule.awaitDispatch(vm::retrieveItemsInitially)

        val expected = TimelinekRepositoryImpl.items
        val actual = vm.items.value?.map { it.origin }

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun reloadItems() {
        val vm = TimelineVM(category, TimelinekRepositoryImpl)

        coroutineRule.awaitDispatch(vm::retrieveItemsInitially)

        val expected = TimelinekRepositoryImpl.items
        val actual = vm.items.value?.map { it.origin }

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getCategory() {
        val vm = TimelineVM(category, TimelinekRepositoryImpl)

        Assert.assertEquals(category, vm.category)
    }

    @Test
    fun showProgress() {
        val vm = TimelineVM(category, TimelinekRepositoryImpl)

        Assert.assertNull(vm.showProgress.value)

        coroutineRule.awaitDispatch(vm::retrieveItemsInitially)

        Assert.assertEquals(false, vm.showProgress.value)
    }
}

/** Dummy repository that response with the same dummy items for any category */
private object TimelinekRepositoryImpl : TimelineRepository {

    val items = listOf(
        TimelineItemModel("id0", "name0", null, TimelineItemStatus.SOLD_OUT, 0F, 0, 0),
        TimelineItemModel("id1", "name1", null, TimelineItemStatus.ON_SALE, 1F, 1, 1),
        TimelineItemModel("id2", "name2", null, TimelineItemStatus.SOLD_OUT, 2F, 2, 2)
                      )

    override suspend fun getItems(category: CategoryModel): List<TimelineItemModel> = items
}