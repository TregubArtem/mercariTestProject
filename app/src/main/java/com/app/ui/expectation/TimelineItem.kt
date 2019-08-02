package com.app.ui.expectation

import android.os.Parcelable
import com.app.api.model.TimelineItemModel
import com.app.api.model.TimelineItemStatus
import com.app.ui.UIExpectation
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

/**
 * Expectation for item of timeline list
 *
 * @param origin model that used like source of other properties
 */
@Parcelize
data class TimelineItem(
    override val origin: TimelineItemModel
                       ) : UIExpectation<TimelineItemModel>, Parcelable {

    companion object {
        private val numberFormat = DecimalFormat("#,###,###,###")
    }

    val coverUri: String? get() = origin.photo
    val name: String get() = origin.name
    val soldOut: Boolean get() = origin.status == TimelineItemStatus.SOLD_OUT
    val likesCount: Int get() = origin.num_likes
    val commentsCount: Int get() = origin.num_comments
    val priceUsd: String get() = numberFormat.format(origin.price)
}