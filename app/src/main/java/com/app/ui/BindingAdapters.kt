package com.app.ui

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.squareup.picasso.Picasso

/**
 * Method for view binding that allow to setup colors
 *
 * @param v target view
 * @param setColors flag that indicate the need
 */
@BindingAdapter("setColors")
fun onSwipeRefreshLayoutSetColors(v: SwipeRefreshLayout, setColors: Boolean?) {
    if (setColors == true)
        v.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
}

/**
 * Method for view binding that allow to set [SwipeRefreshLayout.OnRefreshListener]
 *
 * @param v target view
 * @param listener actual observer of event
 */
@BindingAdapter("refreshListener")
fun onSwipeRefreshLayoutSetOnRefreshListener(v: SwipeRefreshLayout, listener: OnRefreshListener?) {
    v.setOnRefreshListener(listener)
}

/**
 * Method for view binding that allow to change [SwipeRefreshLayout.isRefreshing] state
 *
 * @param v target view
 * @param isRefreshing new state for view
 */
@BindingAdapter("isRefreshing")
fun onSwipeRefreshLayoutSetRefreshing(v: SwipeRefreshLayout, isRefreshing: Boolean?) {
    v.isRefreshing = isRefreshing == true
}

/**
 * Stripped down version of [OnPageChangeListener]
 * Used only for binding adapters purposes
 */
interface OnPageSelectedListener {
    /** Same as [OnPageChangeListener.onPageSelected] */
    fun onPageSelected(position: Int)
}

/**
 * Method for view binding that allow to listen position of current tab
 *
 * @param v target view
 * @param listener actual observer of event
 */
@BindingAdapter("pageSelectedListener")
fun onViewPagerSetPageSelectedListener(v: ViewPager, listener: OnPageSelectedListener?) {
    listener ?: return

    val changeListener = object : OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageSelected(position: Int) =
            listener.onPageSelected(position)
    }
    v.addOnPageChangeListener(changeListener)
}

/**
 * Loads image with [Picasso] into target view
 *
 * @param v target view
 * @param uri path on image
 */
@BindingAdapter("imageUri")
fun onImageViewSetImageUri(v: ImageView, uri: String?) {
    val path = if (uri.isNullOrBlank()) null else uri
    Picasso.get().load(path).into(v)
}