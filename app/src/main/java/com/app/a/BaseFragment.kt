@file:Suppress("RedundantOverride")

package com.app.a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar

/** Basic inheritance for any fragment for that project */
abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected lateinit var vm: VM
        private set

    protected var binding: ViewDataBinding? = null
        protected set

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
    }

    override fun onCreateView(i: LayoutInflater, parent: ViewGroup?, b: Bundle?): View? {
        return super.onCreateView(i, parent, b)
    }

    override fun onViewCreated(v: View, b: Bundle?) {
        super.onViewCreated(v, b)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSaveInstanceState(b: Bundle) {
        super.onSaveInstanceState(b)
    }

    /**
     * Method used to attach view model to fragment in stealth mode
     *
     * @param vm actual view model to attach
     */
    @Suppress("UNCHECKED_CAST")
    @CallSuper
    open fun attachViewModel(vm: BaseVM) {
        this.vm = vm as VM

        observe(vm.errorMessage) {
            it ?: return@observe
            val ctx = context ?: return@observe

            onErrorMessage(it.type, it.getString(ctx))
        }
    }

    /**
     * Method used to attach view data binding to fragment in stealth mode
     *
     * @param binding actual view data binding to attach
     */
    @CallSuper
    open fun attachViewDataBinding(binding: ViewDataBinding) {
        this.binding = binding
    }

    /**
     * Simple implementation to show error message to user
     *
     * @param type unique type of error to operate and handle somehow
     * @param message actual message to show
     */
    protected open fun onErrorMessage(@IdRes type: Int, message: String) {
        val view = view ?: return
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    /** See [BaseActivity.start] */
    protected fun start(fragment: Fragment, toBackStack: Boolean = true) {
        val activity = activity as? BaseActivity ?: return
        activity.start(fragment, toBackStack)
    }
}