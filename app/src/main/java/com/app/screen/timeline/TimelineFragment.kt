package com.app.screen.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.app.a.BaseFragment
import com.app.a.bindView
import com.app.a.observe
import com.app.a.withArguments
import com.app.a.withViewModel
import com.app.api.model.CategoryModel
import com.app.databinding.FragmentTimelineBinding
import com.app.ui.expectation.TimelineItem

/** Class that describes view for timeline list */
class TimelineFragment : BaseFragment<TimelineVM>() {

    companion object {

        private const val EXTRA_CATEGORY = "EXTRA_CATEGORY"

        fun newInstance(category: CategoryModel) = withArguments<TimelineFragment> {
            putParcelable(EXTRA_CATEGORY, category)
        }
    }

    private val adapter by lazy { TimelineAdapter() }

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        @Suppress("RemoveExplicitTypeArguments")
        val category = (b ?: arguments)?.getParcelable<CategoryModel>(EXTRA_CATEGORY) as CategoryModel

        withViewModel({ TimelineVM(category) }) {
            observe(items, ::onItemsUpdate)
        }
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

    private fun onItemsUpdate(list: List<TimelineItem>?) {
        adapter.submitList(list)
    }
}