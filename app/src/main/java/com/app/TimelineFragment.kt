package com.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Class that describes view for timeline list
 */
class TimelineFragment : Fragment() {

    companion object {

        private const val EXTRA_TIMELINE_TYPE = "EXTRA_TIMELINE_TYPE"

        fun newInstance(type: TimelineType) = withArguments<TimelineFragment> {
            putParcelable(EXTRA_TIMELINE_TYPE, type)
        }
    }

    private lateinit var timelineType: TimelineType

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        @Suppress("RemoveExplicitTypeArguments")
        timelineType = (b ?: arguments)?.getParcelable<TimelineType>(EXTRA_TIMELINE_TYPE) as TimelineType
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? =
        View(i.context).apply {
            setBackgroundColor(timelineType.color)
        }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)
        b.putParcelable(EXTRA_TIMELINE_TYPE, timelineType)
    }
}