package com.app.screen.timelineList

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.a.withArguments
import com.app.ui.expectation.TimelineTab

/**
 * Class that describes view for timeline list
 */
class TimelineListFragment : Fragment() {

    companion object {

        private const val EXTRA_TIMELINE_TYPE = "EXTRA_TIMELINE_TYPE"

        fun newInstance(type: TimelineTab) = withArguments<TimelineListFragment> {
            putParcelable(EXTRA_TIMELINE_TYPE, type)
        }
    }

    private lateinit var timelineType: TimelineTab

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        @Suppress("RemoveExplicitTypeArguments")
        timelineType = (b ?: arguments)?.getParcelable<TimelineTab>(EXTRA_TIMELINE_TYPE) as TimelineTab
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        View(i.context).apply {
            setBackgroundColor(Color.GREEN)
        }

    override fun onViewCreated(view: View, b: Bundle?) {
        super.onViewCreated(view, b)
    }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)
        b.putParcelable(EXTRA_TIMELINE_TYPE, timelineType)
    }
}