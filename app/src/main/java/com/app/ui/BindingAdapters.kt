package com.app.ui

import android.graphics.Color
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener

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