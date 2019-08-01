package com.app.screen

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.app.R.id
import com.app.screen.category.CategoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)

        val layout = FrameLayout(this)
        layout.id = id.fragmentContainer

        setContentView(layout)

        if (b == null)
            start(CategoryFragment.newInstance())
    }

    fun start(fragment: Fragment, toBackStack: Boolean = false, container: Int = id.fragmentContainer) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (toBackStack)
            transaction.addToBackStack(fragment.javaClass.name)
        else
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        transaction.replace(container, fragment).commit()
    }
}