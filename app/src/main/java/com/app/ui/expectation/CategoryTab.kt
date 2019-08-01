package com.app.ui.expectation

import android.os.Parcelable
import com.app.api.model.CategoryModel
import com.app.ui.UIExpectation
import kotlinx.android.parcel.Parcelize

/**
 * Expectation for tab element of category screen
 */
@Parcelize
data class CategoryTab(
    override val origin: CategoryModel
                      ) : UIExpectation<CategoryModel>, Parcelable {
    val title: String get() = origin.name
}