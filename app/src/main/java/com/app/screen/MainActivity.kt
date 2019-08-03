package com.app.screen

import android.content.res.Configuration
import android.os.Bundle
import android.widget.FrameLayout
import com.app.R
import com.app.global.BaseActivity
import com.app.screen.categories.CategoriesFragment
import com.app.utility.Analytics

/** Root container of all screens */
class MainActivity : BaseActivity() {

    override val fragmentContainer: Int = R.id.fragmentContainer

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        val layout = FrameLayout(this)
        layout.id = fragmentContainer

        setContentView(layout)

        if (b == null) {
            Analytics.startOf(this)
            start(CategoriesFragment.newInstance())
        }
    }

    override fun onConfigurationChanged(config: Configuration) {
        super.onConfigurationChanged(config)
        Analytics.orientationChanged(config.orientation == Configuration.ORIENTATION_PORTRAIT)
    }
}