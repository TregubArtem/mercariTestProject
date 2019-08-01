package com.app.api.model

import android.os.Parcelable
import androidx.annotation.StringDef
import kotlinx.android.parcel.Parcelize

/** Describes all possible variants for statuses of timeline item */
@StringDef(TimelineItemStatus.ON_SALE, TimelineItemStatus.SOLD_OUT)
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class TimelineItemStatus {
    companion object {
        /** There are more items to be sold */
        const val ON_SALE = "on_sale"
        /** When nothing left and cannot be sold */
        const val SOLD_OUT = "sold_out"
    }
}

/**
 * Representation of element of the timeline list
 *
 * @param id unique number of recording
 * @param name title of the element
 * @param photo uri to external resource to image
 * @param status current state of element (see [TimelineItemStatus])
 * @param price required cost of item
 * @param num_likes how many customers liked that item
 * @param num_comments how many customers leaved comments below item
 */
@Parcelize
data class TimelineItemModel(
    val id: String,
    val name: String,
    val photo: String?,
    @TimelineItemStatus
    val status: String,

    val price: Float,
    val num_likes: Int,
    val num_comments: Int
                            ) : Parcelable