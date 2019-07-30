package com.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.TimelineType.AllTimeline
import com.app.TimelineType.MenTimeline
import com.app.TimelineType.WomenTimeline

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_timeline)

        val adapter = TimelinePagerAdapter(supportFragmentManager) { getString(it) }

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count
        viewPager.currentItem = 1

        //        findViewById<View>(R.id.btnSell).setOnClickListener { }
    }
}

class TimelinePagerAdapter(
    fm: FragmentManager,
    private val stringResTransformation: (Int) -> String
                          ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val timeline = arrayOf(MenTimeline, AllTimeline, WomenTimeline)

    override fun getCount() = timeline.size

    override fun getItem(position: Int): Fragment =
        timeline[position].createFragment()

    override fun getPageTitle(position: Int): CharSequence? =
        stringResTransformation(timeline[position].titleRes)
}