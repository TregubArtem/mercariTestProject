package com.app.ui.expectation

import android.os.Parcelable
import com.app.api.model.TimelineModel
import com.app.ui.UIExpectation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimelineTab(
    override val origin: TimelineModel,
    val title: String = origin.name
                      ) : UIExpectation<TimelineModel>, Parcelable