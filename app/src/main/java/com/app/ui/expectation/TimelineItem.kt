package com.app.ui.expectation

import android.os.Parcelable
import com.app.api.model.TimelineItemModel
import com.app.api.model.TimelineItemStatus
import com.app.ui.UIExpectation
import kotlinx.android.parcel.Parcelize

/**
 * Expectation for item of timeline list
 */
@Parcelize
data class TimelineItem(
    override val origin: TimelineItemModel
                       ) : UIExpectation<TimelineItemModel>, Parcelable {
    val coverUri: String? get() = origin.photo
    val soldOut: Boolean get() = origin.status == TimelineItemStatus.SOLD_OUT
    val likesCount: Int get() = origin.num_likes
    val commentsCount: Int get() = origin.num_comments
    val priceUsd: Int get() = origin.price.toInt()
}