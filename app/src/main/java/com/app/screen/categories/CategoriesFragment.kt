package com.app.screen.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.app.R
import com.app.global.BaseFragment
import com.app.global.bindView
import com.app.global.whenDebug
import com.app.global.withViewModel
import com.app.databinding.FragmentCategoriesBinding
import com.app.ui.OnPageSelectedListener
import com.app.utility.InternetAvailableLiveData
import com.app.utility.observeInternetAvailability

/** Class that describes view for timeline list */
class CategoriesFragment : BaseFragment<CategoriesVM>(), OnClickListener, OnPageSelectedListener {

    companion object {

        private const val EXTRA_CURRENT_PAGE_POSITION = "EXTRA_CURRENT_PAGE_POSITION"

        fun newInstance() = CategoriesFragment()
    }

    private lateinit var miNoInternet: MenuItem

    private val adapter by lazy { CategoriesPagerAdapter(childFragmentManager) }

    private var pagePosition = 0

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        super.setHasOptionsMenu(true)

        if (b != null)
            pagePosition = b.getInt(EXTRA_CURRENT_PAGE_POSITION)

        withViewModel({ CategoriesVM() }) {
            // observeForever is only because adapter could know about updates all the time and have it before ViewPager is created,
            // so effectively restore last position of the tab after rotation
            tabs.observeForever(adapter::bind)
        }
        observeInternetAvailability(::onInternetAvailabilityChange)
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        bindView<FragmentCategoriesBinding>(i, parent) {
            it.adapter = adapter
            it.isRefreshing = vm.showProgress

            it.pageSelectedListener = this
            it.floatingActionClickListener = this
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_categories, menu)

        miNoInternet = menu.findItem(R.id.itemNoInternet)
        miNoInternet.isVisible = !InternetAvailableLiveData.isAvailable
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemNoInternet && adapter.count == 0) {
            vm.reloadTabs()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)
        b.putInt(EXTRA_CURRENT_PAGE_POSITION, pagePosition)
    }

    override fun onClick(v: View) {
        val position = pagePosition
        if (position < adapter.count) {
            val tab = adapter.getCurrentTab(position)
            @Suppress("UNUSED_VARIABLE")
            val category = tab.origin
        }
        whenDebug {
            start(newInstance())
        }
    }

    override fun onPageSelected(position: Int) {
        pagePosition = position
    }

    private fun onInternetAvailabilityChange(available: Boolean?) {
        if (available == true && adapter.count == 0)
            vm.reloadTabs()

        if (::miNoInternet.isInitialized)
            miNoInternet.isVisible = available != true
    }
}