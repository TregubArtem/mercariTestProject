package com.app.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.viewpager.widget.ViewPager
import com.app.R
import com.app.R.layout
import com.app.R.styleable

@ViewPager.DecorView
class CustomActionButton
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, layout.view_custom_action_button, this)

        var text = ""
        var textColor = Color.WHITE

        var src: Drawable? = null
        var tint: ColorStateList? = null

        context.withStyledAttributes(attrs, styleable.CustomActionButton) {
            text = getString(styleable.CustomActionButton_android_text) ?: text
            textColor = getColor(styleable.CustomActionButton_android_textColor, textColor)

            src = getDrawable(styleable.CustomActionButton_android_src)
            tint = getColorStateList(styleable.CustomActionButton_android_tint)
        }
        findViewById<TextView>(R.id.textView).let {
            it.text = text
            it.setTextColor(textColor)
        }
        findViewById<ImageView>(R.id.imageView).let {
            it.setImageDrawable(src)
            it.imageTintList = tint
        }
    }

    override fun setLayoutParams(params: android.view.ViewGroup.LayoutParams?) {
        if (params is ViewPager.LayoutParams)
            params.isDecor = true

        super.setLayoutParams(params)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) =
        super.onMeasure(heightMeasureSpec, heightMeasureSpec)
}