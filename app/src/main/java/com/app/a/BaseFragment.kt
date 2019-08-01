package com.app.a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * Basic inheritance for any fragment for that project
 */
abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected lateinit var vm: VM

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? {
        return super.onCreateView(i, parent, b)
    }

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)
    }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)
    }
}