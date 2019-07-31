package com.app.screen.timelineTabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.R
import com.app.R.layout
import com.app.api.model.TimelineModel
import com.app.screen.timelineList.TimelineListFragment
import com.app.ui.BindingView
import com.app.ui.expectation.TimelineTab

/**
 * Class that describes view for timeline list
 */
class TimelineTabsFragment : Fragment() {

    companion object {
        fun newInstance() = TimelineTabsFragment()
    }

    private val adapter by lazy { TimelinePagerAdapter(childFragmentManager) }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        i.inflate(layout.fragment_timeline_tabs, parent, false)

    override fun onViewCreated(view: View, b: Bundle?) {
        super.onViewCreated(view, b)

        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter


        adapter.bind(
            listOf(
                TimelineTab(TimelineModel("Men", "")),
                TimelineTab(TimelineModel("All", "")),
                TimelineTab(TimelineModel("Women", ""))
                  )
                    )

        viewPager.offscreenPageLimit = adapter.count
        viewPager.currentItem = 1
    }
}

private class TimelinePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT),
    BindingView<List<TimelineTab>> {

    private val tabs = mutableListOf<TimelineTab>()

    override fun bind(data: List<TimelineTab>) {
        tabs.clear()
        tabs.addAll(data)

        notifyDataSetChanged()
    }

    override fun getCount() = tabs.size

    override fun getItem(position: Int): Fragment =
        TimelineListFragment.newInstance(tabs[position])

    override fun getPageTitle(position: Int): CharSequence? =
        tabs[position].title
}