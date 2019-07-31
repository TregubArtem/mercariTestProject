package com.app.a

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.app.BuildConfig
import java.util.concurrent.TimeUnit

inline fun <reified F : Fragment> withArguments(block: Bundle.() -> Unit): F {
    val fragment = F::class.java.newInstance()

    val b = Bundle()
    b.block()

    fragment.arguments = b

    return fragment
}

fun whenDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG)
        block()
}

inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline body: () -> T): T {
    val f = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = body() as VM
    }
    return ViewModelProviders.of(this, f)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.withViewModel(
    crossinline factory: () -> T,
    block: T.(LifecycleOwner) -> Unit = {}
                                                         ): T {
    val vm = getViewModel(factory)

    if (vm is LifecycleObserver)
        lifecycle.addObserver(vm)

    vm.block(this)
    return vm
}

/** Because I can and because it looks like cannon to fire from it */
inline operator fun <reified T> MutableLiveData<T>.remAssign(value: T) =
    postValue(value)

fun <T : Any, L : LiveData<out T>> LifecycleOwner.observe(liveData: L, block: (result: T?) -> Unit) =
    liveData.observe(this, Observer(block))

fun <V : View> Fragment.lazyView(@IdRes id: Int): Lazy<V> =
    LazyFragmentView(this, id)

@Suppress("unused")
private class LazyFragmentView<V : View>(
    private val fragment: Fragment,
    private val id: Int
                                        ) : Lazy<V>, LifecycleObserver {

    private var view: V? = null

    override val value: V
        get() {
            var v = view
            if (v == null) {
                v = fragment.view?.findViewById(id)
                v as V

                view = v
                fragment.viewLifecycleOwner.lifecycle.addObserver(this)
            }
            return v
        }

    override fun isInitialized(): Boolean = view != null

    @OnLifecycleEvent(ON_DESTROY)
    private fun clearValuesOnStop() {
        view = null
    }
}

suspend inline fun TimeUnit.delay(duration: Long) =
    kotlinx.coroutines.delay(toMillis(duration))