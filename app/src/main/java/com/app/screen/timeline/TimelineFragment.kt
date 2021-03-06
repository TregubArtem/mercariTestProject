package com.app.screen.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.app.api.model.CategoryModel
import com.app.databinding.FragmentTimelineBinding
import com.app.global.BaseFragment
import com.app.global.bindView
import com.app.global.toLog
import com.app.global.withArguments
import com.app.global.withViewModel
import com.app.ui.expectation.TimelineItem
import com.app.utility.Analytics
import org.jetbrains.annotations.TestOnly

/** Class that describes view for timeline list */
class TimelineFragment : BaseFragment<TimelineVM>() {

    companion object {

        private const val EXTRA_CATEGORY = "EXTRA_CATEGORY"

        fun newInstance(category: CategoryModel) = withArguments<TimelineFragment> {
            putParcelable(EXTRA_CATEGORY, category)
        }

        @TestOnly
        fun newArguments(category: CategoryModel): Bundle {
            val bundle = Bundle(1)
            bundle.putParcelable(EXTRA_CATEGORY, category)

            return bundle
        }
    }

    private val adapter by lazy { TimelineBindingAdapter(::onTimelineItemClick) }

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        @Suppress("RemoveExplicitTypeArguments")
        val category = (b ?: arguments)?.getParcelable<CategoryModel>(EXTRA_CATEGORY) as CategoryModel

        withViewModel({ TimelineVM(category) }) {
            // observeForever used because of adapter already able to deliver data in fine moment
            items.observeForever(adapter::submitList)
        }
        if (b == null)
            Analytics.startOf(this)
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        bindView<FragmentTimelineBinding>(i, parent) {
            it.isRefreshing = vm.showProgress
            it.setRefreshListener(vm::reloadItems)

            it.adapter = adapter
            it.layoutManager = GridLayoutManager(i.context, 2)
        }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)
        b.putParcelable(EXTRA_CATEGORY, vm.category)
    }

    private fun onTimelineItemClick(item: TimelineItem) {
        Analytics.clickOn("timelineItem")
        toLog(item)
    }

    override fun toString(): String = vm.category.name
}