package com.app.screen.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.app.R
import com.app.a.BaseFragment
import com.app.a.attempt
import com.app.a.bindView
import com.app.a.lazyView
import com.app.a.observe
import com.app.a.whenDebug
import com.app.a.withViewModel
import com.app.databinding.FragmentCategoriesBinding
import com.app.ui.expectation.CategoryTab
import com.app.utility.InternetAvailableLiveData

/** Class that describes view for timeline list */
class CategoriesFragment : BaseFragment<CategoriesVM>(), OnClickListener {

    companion object {

        private const val EXTRA_CURRENT_ITEM_INDEX = "EXTRA_CURRENT_ITEM_INDEX"

        fun newInstance() = CategoriesFragment()
    }

    private val viewPager by lazyView<ViewPager>(R.id.viewPager)
    private lateinit var miNoInternet: MenuItem

    private val adapter by lazy { CategoriesPagerAdapter(childFragmentManager) }

    private var currentItem = 0

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        super.setHasOptionsMenu(true)

        if (b != null)
            currentItem = b.getInt(EXTRA_CURRENT_ITEM_INDEX)

        withViewModel({ CategoriesVM() }) {
            observe(tabs, ::onTabsUpdate)

            InternetAvailableLiveData.observe(context!!, this@CategoriesFragment) { available ->
                if (available == true && adapter.count == 0)
                    vm.reloadTabs()

                if (::miNoInternet.isInitialized)
                    miNoInternet.isVisible = available != true
            }
        }
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        bindView<FragmentCategoriesBinding>(i, parent) {
            it.adapter = adapter
            it.isRefreshing = vm.showProgress
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

    override fun onDestroyView() {
        super.onDestroyView()
        currentItem = viewPager.currentItem
    }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)

        attempt<TypeCastException> {
            currentItem = viewPager.currentItem
        }
        b.putInt(EXTRA_CURRENT_ITEM_INDEX, currentItem)
    }

    private fun onTabsUpdate(list: List<CategoryTab>?) {
        adapter.bind(list ?: emptyList())
        val count = adapter.count

        if (count > 0) {
            viewPager.offscreenPageLimit = count
            viewPager.currentItem = currentItem
        }
    }

    override fun onClick(v: View) {
        val item = viewPager.currentItem
        if (item < adapter.count) {
            val tab = adapter.getCurrentTab(item)
            @Suppress("UNUSED_VARIABLE")
            val category = tab.origin
        }
        whenDebug {
            start(newInstance())
        }
    }
}