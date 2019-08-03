package com.app.api.model

import org.junit.Assert
import org.junit.Test

/** To prevent modification errors in future */
class TimelineItemModelTest {

    @Test
    fun getId() {
        val id = "id"
        val model = TimelineItemModel(id, "", null, "", 0F, 0, 0)

        Assert.assertEquals(id, model.id)
    }

    @Test
    fun getName() {
        val name = "name"
        val model = TimelineItemModel("", name, null, "", 0F, 0, 0)

        Assert.assertEquals(name, model.name)
    }

    @Test
    fun getPhoto() {
        val photo = "photo"
        val model = TimelineItemModel("", "", photo, "", 0F, 0, 0)

        Assert.assertEquals(photo, model.photo)
    }

    @Test
    fun getStatus() {
        val status = TimelineItemStatus.ON_SALE
        val model = TimelineItemModel("", "", null, status, 0F, 0, 0)

        Assert.assertEquals(status, model.status)
    }

    @Test
    fun getPrice() {
        val price = 123F
        val model = TimelineItemModel("", "", null, "", price, 0, 0)

        Assert.assertEquals(price, model.price)
    }

    @Test
    fun getNum_likes() {
        val likes = 123
        val model = TimelineItemModel("", "", null, "", 0F, likes, 0)

        Assert.assertEquals(likes, model.num_likes)
    }

    @Test
    fun getNum_comments() {
        val comments = 123
        val model = TimelineItemModel("", "", null, "", 0F, 0, comments)

        Assert.assertEquals(comments, model.num_comments)
    }

    @Test
    fun equals() {
        val model0 = TimelineItemModel("id0", "name0", null, TimelineItemStatus.ON_SALE, 0F, 0, 0)
        val model1 = TimelineItemModel("id0", "name0", null, TimelineItemStatus.ON_SALE, 0F, 0, 0)

        Assert.assertEquals(model0, model1)
    }

    @Test
    fun notEquals() {
        val model0 = TimelineItemModel("id0", "name0", null, TimelineItemStatus.ON_SALE, 0F, 0, 0)
        val model1 = TimelineItemModel("id1", "name1", null, TimelineItemStatus.SOLD_OUT, 1F, 1, 1)

        Assert.assertNotEquals(model0, model1)
    }
}