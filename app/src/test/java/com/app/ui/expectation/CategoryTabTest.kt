package com.app.ui.expectation

import com.app.api.model.CategoryModel
import org.junit.Assert
import org.junit.Test

/** To prevent modification errors in future */
class CategoryTabTest {

    @Test
    fun getTitle() {
        val title = "title"
        val model = CategoryModel(title, "")
        val tab = CategoryTab(model)

        Assert.assertEquals(title, tab.title)
    }

    @Test
    fun getOrigin() {
        val model = CategoryModel("name", "data")
        val tab = CategoryTab(model)

        Assert.assertEquals(model, tab.origin)
    }

    @Test
    fun equals() {
        val model0 = CategoryModel("name0", "data0")
        val tab0 = CategoryTab(model0)

        val model1 = CategoryModel("name0", "data0")
        val tab1 = CategoryTab(model1)

        Assert.assertEquals(tab0, tab1)
    }

    @Test
    fun notEquals() {
        val model0 = CategoryModel("name0", "data0")
        val tab0 = CategoryTab(model0)

        val model1 = CategoryModel("name1", "data1")
        val tab1 = CategoryTab(model1)

        Assert.assertNotEquals(tab0, tab1)
    }
}