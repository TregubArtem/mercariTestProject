package com.app.screen.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.api.model.CategoryModel
import com.app.repository.CategoriesRepository
import com.app.utility.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

/** Check key behaviour correctness work */
@ExperimentalCoroutinesApi
class CategoriesVMTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val liveDataRule = InstantTaskExecutorRule()

    @Test
    fun retrieveTabsInitially() {
        val vm = CategoriesVM(CategoriesRepositoryImpl)

        coroutineRule.awaitDispatch(vm::retrieveTabsInitially)

        val expected = CategoriesRepositoryImpl.categories
        val actual = vm.tabs.value?.map { it.origin }

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun reloadTabs() {
        val vm = CategoriesVM(CategoriesRepositoryImpl)

        coroutineRule.awaitDispatch(vm::reloadTabs)

        val expected = CategoriesRepositoryImpl.categories
        val actual = vm.tabs.value?.map { it.origin }

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun showProgress() {
        val vm = CategoriesVM(CategoriesRepositoryImpl)

        Assert.assertNull(vm.showProgress.value)

        coroutineRule.awaitDispatch(vm::retrieveTabsInitially)

        Assert.assertEquals(false, vm.showProgress.value)
    }
}

/** Dummy repository that response with dummy categories */
private object CategoriesRepositoryImpl : CategoriesRepository {

    val categories = listOf(
        CategoryModel("name0", "data0"),
        CategoryModel("name1", "data1"),
        CategoryModel("name2", "data2")
                           )

    override suspend fun getCategories(): List<CategoryModel> = categories
}