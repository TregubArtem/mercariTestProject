package com.app.screen

import android.os.Bundle
import android.widget.FrameLayout
import com.app.R
import com.app.a.BaseActivity
import com.app.screen.categories.CategoriesFragment

/** Root container of all screens */
class MainActivity : BaseActivity() {

    override val fragmentContainer: Int = R.id.fragmentContainer

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        val layout = FrameLayout(this)
        layout.id = fragmentContainer

        setContentView(layout)

        if (b == null)
            start(CategoriesFragment.newInstance())
    }
}