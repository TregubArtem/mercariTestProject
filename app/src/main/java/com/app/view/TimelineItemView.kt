package com.app.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.R
import com.app.ui.BindingView
import com.app.ui.expectation.TimelineItem
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

/**
 * Presentation of timeline element of the list
 */
class TimelineItemView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs),
    BindingView<TimelineItem>, OnClickListener {

    private val ivCover by lazyView<ImageView>(R.id.ivCover)

    private val ivLabelSold by lazyView<View>(R.id.ivLabelSold)

    private val tvLikes by lazyView<TextView>(R.id.tvLikes)
    private val tvComments by lazyView<TextView>(R.id.tvComments)
    private val tvPrice by lazyView<TextView>(R.id.tvPrice)

    private val numberFormat = DecimalFormat("#,###,###,###")

    init {
        View.inflate(context, R.layout.view_timeline_item, this)

        val padding = resources.getDimensionPixelSize(R.dimen.spacing_d2)
        setPadding(padding, padding, padding, padding)

        elevation = resources.getDimension(R.dimen.elevation)
        setBackgroundColor(Color.WHITE)

        findViewById<View>(R.id.viewForeground).setOnClickListener(this)
    }

    /** Used to bind data to view, so the properties be presented to user */
    override fun bind(data: TimelineItem) {
        tag = data

        Picasso.get().load(data.coverUri).into(ivCover)
        ivLabelSold.isVisible = data.soldOut
        tvLikes.text = numberFormat.format(data.likesCount)
        tvComments.text = numberFormat.format(data.commentsCount)
        tvPrice.text = resources.getString(R.string.timeline_price_format, numberFormat.format(data.priceUsd))
    }

    /** Overrated because of possibility to set single click listener to this view, so click will look fancy */
    override fun setOnClickListener(l: OnClickListener?) {
        findViewById<View>(R.id.viewForeground).setOnClickListener(l)
    }

    override fun onClick(v: View) {
        @Suppress("UNUSED_VARIABLE")
        val item = tag as TimelineItem
    }

    fun createViewHolder(): TimelineItemViewHolder {
        val margin = resources.getDimensionPixelSize(R.dimen.spacing_d2)

        val params = MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(margin, margin, margin, margin)

        layoutParams = params
        return TimelineItemViewHolder(this)
    }

}

class TimelineItemViewHolder(view: TimelineItemView) : ViewHolder(view), BindingView<TimelineItem> {

    override fun bind(data: TimelineItem) {
        (itemView as TimelineItemView).bind(data)
    }
}