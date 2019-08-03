package com.app.view

import android.view.View
import androidx.annotation.IdRes

/**
 *  Allow find view at the moment when it called
 *
 * @param id unique key to find existing view
 * @return view only in the moment it is called
 */
@Suppress("RemoveExplicitTypeArguments")
inline fun <reified V : View> View.lazyView(@IdRes id: Int): Lazy<V> =
    lazy { findViewById<V>(id) }