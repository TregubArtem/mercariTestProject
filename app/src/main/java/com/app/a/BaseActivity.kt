package com.app.a

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/** Basic inheritance for any activity of that project */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract val fragmentContainer: Int

    /**
     * Method allow to run any fragment in simple way
     *
     * @param fragment target to launch
     * @param toBackStack flag that allow to save instance in stack
     * @param container destination of where fragment should be placed
     */
    fun start(fragment: Fragment, toBackStack: Boolean = false, container: Int = fragmentContainer) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        if (toBackStack)
            transaction.addToBackStack(fragment.javaClass.name)
        else
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        transaction.replace(container, fragment).commit()
    }
}