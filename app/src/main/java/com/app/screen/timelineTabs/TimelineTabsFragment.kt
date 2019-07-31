package com.app.screen.timelineTabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.R
import com.app.a.lazyView
import com.app.a.observe
import com.app.a.withViewModel
import com.app.screen.timelineList.TimelineListFragment
import com.app.ui.BindingView
import com.app.ui.expectation.TimelineTab

/**
 * Class that describes view for timeline list
 */
class TimelineTabsFragment : Fragment(), OnClickListener {

    companion object {
        fun newInstance() = TimelineTabsFragment()
    }

    private lateinit var vm: TimelineTabsViewModel

    private val viewPager by lazyView<ViewPager>(R.id.viewPager)
    private val progressBar by lazyView<View>(R.id.progressBar)

    private val adapter by lazy { TimelinePagerAdapter(childFragmentManager) }

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        vm = withViewModel({ TimelineTabsViewModel() }) {
            observe(tabs, ::onTabsUpdate)
            observe(showProgress, ::onShowProgress)
        }
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        i.inflate(R.layout.fragment_timeline_tabs, parent, false)

    override fun onViewCreated(view: View, b: Bundle?) {
        super.onViewCreated(view, b)

        view.findViewById<View>(R.id.btnSell).setOnClickListener(this)
        viewPager.adapter = adapter
    }

    private fun onTabsUpdate(list: List<TimelineTab>?) {
        val tabs = list ?: emptyList()
        adapter.bind(tabs)

        if (tabs.isNotEmpty()) {
            viewPager.offscreenPageLimit = tabs.size
            viewPager.currentItem = tabs.size / 2
        }
    }

    private fun onShowProgress(show: Boolean?) {
        progressBar.isVisible = show == true
    }

    override fun onClick(v: View) {
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