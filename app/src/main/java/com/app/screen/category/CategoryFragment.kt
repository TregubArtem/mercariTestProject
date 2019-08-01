package com.app.screen.category

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
import com.app.a.BaseFragment
import com.app.a.lazyView
import com.app.a.observe
import com.app.a.withViewModel
import com.app.screen.timeline.TimelineFragment
import com.app.ui.BindingView
import com.app.ui.expectation.CategoryTab

/**
 * Class that describes view for timeline list
 */
class CategoryFragment : BaseFragment<CategoryVM>(), OnClickListener {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private val viewPager by lazyView<ViewPager>(R.id.viewPager)
    private val progressBar by lazyView<View>(R.id.progressBar)

    private val adapter by lazy { CategoryPagerAdapter(childFragmentManager) }

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        vm = withViewModel({ CategoryVM() }) {
            observe(tabs, ::onTabsUpdate)
            observe(showProgress, ::onShowProgress)
        }
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        i.inflate(R.layout.fragment_category, parent, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)

        v.findViewById<View>(R.id.btnSell).setOnClickListener(this)
        viewPager.adapter = adapter
    }

    private fun onTabsUpdate(list: List<CategoryTab>?) {
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
        val item = viewPager.currentItem
        val tab = adapter.getCurrentTab(item)

        @Suppress("UNUSED_VARIABLE")
        val category = tab.origin
    }
}

private class CategoryPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT),
    BindingView<List<CategoryTab>> {

    private val tabs = mutableListOf<CategoryTab>()

    override fun bind(data: List<CategoryTab>) {
        tabs.clear()
        tabs.addAll(data)

        notifyDataSetChanged()
    }

    fun getCurrentTab(position: Int) = tabs[position]

    override fun getCount() = tabs.size

    override fun getItem(position: Int): Fragment =
        TimelineFragment.newInstance(tabs[position].origin)

    override fun getPageTitle(position: Int): CharSequence? =
        tabs[position].title
}