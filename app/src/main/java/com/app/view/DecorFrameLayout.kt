package com.app.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager

@ViewPager.DecorView
class DecorFrameLayout
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    override fun setLayoutParams(params: android.view.ViewGroup.LayoutParams?) {
        if (params is ViewPager.LayoutParams)
            params.isDecor = true

        super.setLayoutParams(params)
    }
}