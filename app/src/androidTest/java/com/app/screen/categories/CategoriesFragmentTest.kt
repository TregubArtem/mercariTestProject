package com.app.screen.categories

import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.viewpager.widget.ViewPager
import com.app.R
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/** Check key elements and behaviour existence */
@RunWith(AndroidJUnit4::class)
class CategoriesFragmentTest {

    @Test
    fun launchFragment() {
        val tag = CategoriesFragment.getTargetTag()
        val scenario = launchFragmentInContainer(themeResId = R.style.AppTheme) { CategoriesFragment.newInstance() }

        scenario.onFragment { fragment ->
            Assert.assertEquals(tag, fragment.toString())
        }
    }

    @Test
    fun actionButtonText() {
        launchFragmentInContainer(themeResId = R.style.AppTheme) { CategoriesFragment.newInstance() }
        // R.id.textView is inner view for R.id.btnSell
        onView(withId(R.id.textView)).check(matches(withText(R.string.timeline_sell)))
    }

    @Test
    fun viewPagerAdapterIsFragmentPagerAdapter() {
        launchFragmentInContainer(themeResId = R.style.AppTheme) { CategoriesFragment.newInstance() }
        // R.id.textView is inner view for R.id.btnSell
        onView(withId(R.id.viewPager)).check { view, _ ->
            Assert.assertThat(view, Matchers.instanceOf(ViewPager::class.java))
            view as ViewPager

            Assert.assertThat(view.adapter, Matchers.instanceOf(FragmentPagerAdapter::class.java))
        }
    }
}