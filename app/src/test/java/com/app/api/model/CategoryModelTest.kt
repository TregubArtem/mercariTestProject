package com.app.api.model

import org.junit.Assert
import org.junit.Test

/** To prevent modification errors in future */
class CategoryModelTest {

    @Test
    fun getName() {
        val name = "name"
        val model = CategoryModel(name, "")

        Assert.assertEquals(name, model.name)
    }

    @Test
    fun getData() {
        val data = "data"
        val model = CategoryModel("", data)

        Assert.assertEquals(data, model.data)
    }

    @Test
    fun equals() {
        val model0 = CategoryModel("name0", "data0")
        val model1 = CategoryModel("name0", "data0")

        Assert.assertEquals(model0, model1)
    }

    @Test
    fun notEquals() {
        val model0 = CategoryModel("name0", "data0")
        val model1 = CategoryModel("name1", "data1")

        Assert.assertNotEquals(model0, model1)
    }
}