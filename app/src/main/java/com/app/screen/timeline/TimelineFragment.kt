package com.app.screen.timeline

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.app.R
import com.app.a.BaseFragment
import com.app.a.lazyView
import com.app.a.observe
import com.app.a.withArguments
import com.app.a.withViewModel
import com.app.api.model.CategoryModel
import com.app.ui.expectation.TimelineItem
import com.app.view.TimelineItemView
import com.app.view.TimelineItemViewHolder

/** Class that describes view for timeline list */
class TimelineFragment : BaseFragment<TimelineVM>(), OnRefreshListener {

    companion object {

        private const val EXTRA_CATEGORY = "EXTRA_CATEGORY"

        fun newInstance(category: CategoryModel) = withArguments<TimelineFragment> {
            putParcelable(EXTRA_CATEGORY, category)
        }
    }

    private val refreshLayout by lazyView<SwipeRefreshLayout>(R.id.refreshLayout)
    private val recyclerView by lazyView<RecyclerView>(R.id.recyclerView)

    private val adapter by lazy { TimelineAdapter() }

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        @Suppress("RemoveExplicitTypeArguments")
        val category = (b ?: arguments)?.getParcelable<CategoryModel>(EXTRA_CATEGORY) as CategoryModel

        withViewModel({ TimelineVM(category) }) {
            observe(items, ::onItemsUpdate)
            observe(showProgress, ::onShowProgress)
        }
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        i.inflate(R.layout.fragment_timeline, parent, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        refreshLayout.setOnRefreshListener(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(v.context, 2)
    }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)
        b.putParcelable(EXTRA_CATEGORY, vm.category)
    }

    private fun onItemsUpdate(list: List<TimelineItem>?) {
        adapter.submitList(list)
    }

    private fun onShowProgress(show: Boolean?) {
        refreshLayout.isRefreshing = show == true
    }

    override fun onRefresh() {
        vm.reloadItems()
    }
}

/** Required class to use with [ListAdapter] */
private class TimelineDiffCallback : DiffUtil.ItemCallback<TimelineItem>() {

    override fun areItemsTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean = true
}

/** Class to help [RecyclerView] to show views. Exactly that is showing items for timeline list of some category. */
private class TimelineAdapter : ListAdapter<TimelineItem, TimelineItemViewHolder>(TimelineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineItemViewHolder =
        TimelineItemView(parent.context).createViewHolder()

    override fun onBindViewHolder(holder: TimelineItemViewHolder, position: Int) =
        holder.bind(getItem(position))
}