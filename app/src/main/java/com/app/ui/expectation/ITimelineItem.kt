package com.app.ui.expectation

import com.app.api.model.TimelineItemModel
import com.app.ui.UIExpectation

/**
 * This is expectation that will be applied explicitly and right away
 */
interface ITimelineItem : UIExpectation<TimelineItemModel> {
    val coverUri: String?
    val soldOut: Boolean
    val likesCount: Int
    val commentsCount: Int
    val priceUsd: Int
}