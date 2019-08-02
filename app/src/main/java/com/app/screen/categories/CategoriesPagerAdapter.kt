package com.app.screen.categories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.screen.timeline.TimelineFragment
import com.app.ui.BindingView
import com.app.ui.expectation.CategoryTab

/**
 * Required class to help show tabs content with [ViewPager]
 *
 * @param fm manager to handle attachment of content fragments
 */
class CategoriesPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT),
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