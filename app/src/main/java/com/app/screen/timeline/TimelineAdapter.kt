package com.app.screen.timeline

import android.content.res.Resources
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.global.BaseBindingAdapter
import com.app.global.classAction
import com.app.databinding.ViewTimelineItemBinding
import com.app.ui.expectation.TimelineItem

/**
 * Class to help [RecyclerView] to show views
 * Exactly that class responsible for showing items of timeline
 */
class TimelineBindingAdapter(
    private val onItemClick: (TimelineItem) -> Unit
                            ) : BaseBindingAdapter<TimelineItem>(TimelineDiffCallback()), OnClickListener {

    override val classAction = classAction<ViewTimelineItemBinding, TimelineItem> { item, _ ->
        this.item = item
        clickListener = this@TimelineBindingAdapter
    }

    override fun setLayoutParams(target: View, resources: Resources) {
        val margin = resources.getDimensionPixelSize(R.dimen.spacing_d2)

        val params = MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(margin, margin, margin, margin)

        target.layoutParams = params
    }

    override fun onClick(v: View) {
        val tag = v.tag as TimelineItem
        onItemClick(tag)
    }
}

/** Required class to use with [ListAdapter] */
private class TimelineDiffCallback : DiffUtil.ItemCallback<TimelineItem>() {

    override fun areItemsTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean = true
}