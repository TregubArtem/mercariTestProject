package com.app

import android.graphics.Color
import android.os.Parcelable
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

/** Root class that describes all possible variants of timeline */
sealed class TimelineType(@StringRes val titleRes: Int, @ColorInt val color: Int) : Parcelable {

    fun createFragment(): TimelineFragment = TimelineFragment.newInstance(this)

    @Parcelize
    object MenTimeline : TimelineType(R.string.timeline_men, Color.RED)

    @Parcelize
    object AllTimeline : TimelineType(R.string.timeline_all, Color.GREEN)

    @Parcelize
    object WomenTimeline : TimelineType(R.string.timeline_women, Color.BLUE)
}