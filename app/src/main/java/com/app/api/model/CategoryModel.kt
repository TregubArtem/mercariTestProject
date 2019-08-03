package com.app.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Representation of tab label and it's list data
 *
 * @param name label of the tab
 * @param data url to get data for the tab's list
 */
@Parcelize
data class CategoryModel(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "data")
    val data: String
                        ) : Parcelable