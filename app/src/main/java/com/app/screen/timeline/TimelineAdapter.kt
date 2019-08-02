package com.app.screen.timeline

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ui.expectation.TimelineItem
import com.app.view.item.TimelineItemView
import com.app.view.item.TimelineItemViewHolder

/** Class to help [RecyclerView] to show views. Exactly that is showing items for timeline list of some category. */
class TimelineAdapter : ListAdapter<TimelineItem, TimelineItemViewHolder>(TimelineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineItemViewHolder =
        TimelineItemView(parent.context).createViewHolder()

    override fun onBindViewHolder(holder: TimelineItemViewHolder, position: Int) =
        holder.bind(getItem(position))
}

/** Required class to use with [ListAdapter] */
private class TimelineDiffCallback : DiffUtil.ItemCallback<TimelineItem>() {

    override fun areItemsTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean = true
}