package com.app.ui.expectation

import com.app.api.model.TimelineItemModel
import com.app.api.model.TimelineItemStatus
import org.junit.Assert
import org.junit.Test

/** To prevent modification errors in future */
class TimelineItemTest {

    @Test
    fun getCoverUri() {
        val coverUri = "coverUri"
        val model = TimelineItemModel("", "", coverUri, "", 0F, 0, 0)
        val item = TimelineItem(model)

        Assert.assertEquals(coverUri, item.coverUri)
    }

    @Test
    fun getName() {
        val name = "name"
        val model = TimelineItemModel("", name, null, "", 0F, 0, 0)
        val item = TimelineItem(model)

        Assert.assertEquals(name, item.name)
    }

    @Test
    fun getSoldOut() {
        val soldOut = true
        val model = TimelineItemModel("", "", null, TimelineItemStatus.SOLD_OUT, 0F, 0, 0)
        val item = TimelineItem(model)

        Assert.assertEquals(soldOut, item.soldOut)
    }

    @Test
    fun getLikesCount() {
        val likesCount = 123
        val model = TimelineItemModel("", "", null, "", 0F, likesCount, 0)
        val item = TimelineItem(model)

        Assert.assertEquals(likesCount, item.likesCount)
    }

    @Test
    fun getCommentsCount() {
        val commentsCount = 123
        val model = TimelineItemModel("", "", null, "", 0F, 0, commentsCount)
        val item = TimelineItem(model)

        Assert.assertEquals(commentsCount, item.commentsCount)
    }

    @Test
    fun getPriceUsd() {
        val priceUsd = 123F
        val model = TimelineItemModel("", "", null, "", priceUsd, 0, 0)
        val item = TimelineItem(model)

        Assert.assertEquals(TimelineItem.formatPrice(priceUsd), item.priceUsd)
    }

    @Test
    fun getOrigin() {
        val model = TimelineItemModel("id", "name", null, TimelineItemStatus.ON_SALE, 123F, 123, 123)
        val item = TimelineItem(model)

        Assert.assertEquals(model, item.origin)
    }

    @Test
    fun equals() {
        val model0 = TimelineItemModel("id0", "name0", null, TimelineItemStatus.ON_SALE, 0F, 0, 0)
        val item0 = TimelineItem(model0)

        val model1 = TimelineItemModel("id0", "name0", null, TimelineItemStatus.ON_SALE, 0F, 0, 0)
        val item1 = TimelineItem(model1)

        Assert.assertEquals(item0, item1)
    }

    @Test
    fun notEquals() {
        val model0 = TimelineItemModel("id0", "name0", null, TimelineItemStatus.ON_SALE, 0F, 0, 0)
        val item0 = TimelineItem(model0)

        val model1 = TimelineItemModel("id1", "name1", null, TimelineItemStatus.SOLD_OUT, 1F, 1, 1)
        val item1 = TimelineItem(model1)

        Assert.assertNotEquals(item0, item1)
    }
}