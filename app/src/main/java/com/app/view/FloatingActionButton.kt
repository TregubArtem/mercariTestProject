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
import com.app.R

/** To reach visual requirements there was no other way, but only to implement custom view */
class FloatingActionButton
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_custom_action_button, this)

        var text = ""
        var textColor = Color.WHITE

        var src: Drawable? = null
        var tint: ColorStateList? = null

        context.withStyledAttributes(attrs, R.styleable.FloatingActionButton) {
            text = getString(R.styleable.FloatingActionButton_android_text) ?: text
            textColor = getColor(R.styleable.FloatingActionButton_android_textColor, textColor)

            src = getDrawable(R.styleable.FloatingActionButton_android_src)
            tint = getColorStateList(R.styleable.FloatingActionButton_android_tint)
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
}