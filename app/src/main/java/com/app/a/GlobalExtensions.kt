package com.app.a

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
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
import com.crashlytics.android.Crashlytics
import java.util.concurrent.TimeUnit

/**
 * Method to quickly wrap fragment with custom arguments
 *
 * @param block easy way to setup all required arguments
 * @return instance of required fragment
 */
inline fun <reified F : Fragment> withArguments(block: Bundle.() -> Unit): F {
    val fragment = F::class.java.newInstance()

    val b = Bundle()
    b.block()

    fragment.arguments = b

    return fragment
}

/** Should be called only for development purposes */
inline fun whenDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG)
        block()
}

/**
 * Method to obtain instance of required view model
 *
 * @param block it is method with constructor for view model that will be called when instance is not exists yet
 * @return required view model
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline block: () -> T): T {
    val f = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = block() as VM
    }
    return ViewModelProviders.of(this, f)[T::class.java]
}

/**
 * Method that allow retrieve view models in shorter manner
 *
 * @param factory it is method with constructor for view model that will be called when instance is not exists yet
 * @param block is the key place to setup observers on data of view model
 * @return required view model
 */
inline fun <reified T : ViewModel> Fragment.withViewModel(
    crossinline factory: () -> T,
    block: T.() -> Unit = {}
                                                         ): T {
    val vm = getViewModel(factory)

    if (vm is LifecycleObserver)
        lifecycle.addObserver(vm)

    if (this is BaseFragment<*> && vm is BaseVM)
        attachViewModel(vm)

    vm.block()
    return vm
}

/**
 * Because I can and because it looks like cannon to fire from it
 *
 * @param value anything that should be set to live data
 */
inline operator fun <reified T> MutableLiveData<T>.remAssign(value: T?) =
    postValue(value)

fun <T : Any, L : LiveData<out T>> LifecycleOwner.observe(liveData: L, block: (result: T?) -> Unit) =
    liveData.observe(this, Observer(block))

/**
 * Simple way to delay time in corouitine
 *
 * @param duration that should be converted to millis
 */
suspend inline fun TimeUnit.delay(duration: Long) =
    kotlinx.coroutines.delay(toMillis(duration))

/**
 * Logging method
 *
 * @param any something that should be sent to log
 * @param tag tag under which message should be showed
 */
fun toLog(any: Any?, tag: String = "mLog") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, any.toString())

        if (any is Throwable)
            any.printStackTrace()

    } else if (any is Throwable) {
        Crashlytics.logException(any)
    }
}

/**
 * Hacky way to use views for fragment only in time when it needed.
 * Reference on view will be purged instantly after fragment call [Fragment.onDestroyView]
 *
 * @param id unique key to find existing view
 * @return view only in the moment it is called
 */
fun <V : View> Fragment.lazyView(@IdRes id: Int): Lazy<V> =
    LazyFragmentView(this, id)

/**
 *  Implementation for method [Fragment.lazyView]
 *
 * @param fragment target source to search for view
 * @param id unique key to find existing view
 */
private class LazyFragmentView<V : View>(
    private val fragment: Fragment,
    private val id: Int
                                        ) : Lazy<V>, LifecycleObserver {

    private var view: V? = null

    init {
        fragment.observe(fragment.viewLifecycleOwnerLiveData) {
            it?.lifecycle?.addObserver(this@LazyFragmentView)
        }
    }

    override val value: V
        get() {
            var v = view
            if (v == null) {
                v = fragment.view?.findViewById(id)
                v as V

                view = v
            }
            return v
        }

    override fun isInitialized(): Boolean = view != null

    @Suppress("unused")
    @OnLifecycleEvent(ON_DESTROY)
    private fun clearReference() {
        view = null
    }
}

/**
 * Method allow to obtain binding class in short way.
 * Do not forget to add this part to proguard or wait for the crash
 *
 *   -keepclassmembers class * extends androidx.databinding.ViewDataBinding {
 *   public static *** inflate(...);
 *   }
 *
 * @param i inflater for view
 * @param parent root view if exists
 * @return required binding instance
 */
inline fun <reified B : ViewDataBinding> Class<B>.getBinding(i: LayoutInflater, parent: ViewGroup?): B {
    val inflate = this.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
    val binding = inflate.invoke(null, i, parent, false)

    if (binding is B)
        return binding
    else
        throw IllegalStateException("View binding error")
}

/**
 * Method allow to obtain binding view and also attach it to somewhere
 *
 * @param i inflater for view
 * @param parent root view if exists
 * @param block where you can enable variables and other stuff
 * @return required binding instance
 */
inline fun <reified B : ViewDataBinding> Any.bindView(
    i: LayoutInflater,
    parent: ViewGroup?,
    block: (binding: B) -> Unit
                                                     ): View {
    val binding = B::class.java.getBinding(i, parent)

    if (this is LifecycleOwner)
        binding.setLifecycleOwner(this)

    if (this is BaseFragment<*>)
        attachViewDataBinding(binding)

    block(binding)

    binding.executePendingBindings()
    return binding.root
}