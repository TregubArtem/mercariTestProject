package com.app.screen.timeline

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.R.id
import com.app.api.model.CategoryModel
import com.app.global.BaseBindingAdapter
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/** Check key elements and behaviour existence */
@RunWith(AndroidJUnit4::class)
class TimelineFragmentTest {

    @Test
    fun launchFragment() {
        val categoryName = "Mock"
        val category = CategoryModel(categoryName, "test.json")
        val arguments = TimelineFragment.newArguments(category)
        val scenario = launchFragmentInContainer(arguments) { TimelineFragment() }

        scenario.onFragment { fragment ->
            Assert.assertEquals(categoryName, fragment.toString())
        }
    }

    @Test
    fun recreateFragment() {
        val categoryName = "Mock"
        val category = CategoryModel(categoryName, "test.json")
        val arguments = TimelineFragment.newArguments(category)
        val scenario = launchFragmentInContainer(arguments) { TimelineFragment() }

        scenario.recreate().onFragment { fragment ->
            Assert.assertEquals(categoryName, fragment.toString())
        }
    }

    @Test
    fun layoutManageIsGrid() {
        val category = CategoryModel("Mock", "test.json")
        val arguments = TimelineFragment.newArguments(category)
        launchFragmentInContainer(arguments) { TimelineFragment() }

        onView(withId(id.recyclerView)).check { view, _ ->
            Assert.assertThat(view, Matchers.instanceOf(RecyclerView::class.java))
            view as RecyclerView

            Assert.assertThat(view.layoutManager, Matchers.instanceOf(GridLayoutManager::class.java))
        }
    }

    @Test
    fun recyclerViewAdapterIsBinding() {
        val category = CategoryModel("Mock", "test.json")
        val arguments = TimelineFragment.newArguments(category)
        launchFragmentInContainer(arguments) { TimelineFragment() }

        onView(withId(id.recyclerView)).check { view, _ ->
            Assert.assertThat(view, Matchers.instanceOf(RecyclerView::class.java))
            view as RecyclerView

            Assert.assertThat(view.adapter, Matchers.instanceOf(BaseBindingAdapter::class.java))
        }
    }
}