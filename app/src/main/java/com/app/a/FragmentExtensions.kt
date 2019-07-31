package com.app.a

import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <reified F : Fragment> withArguments(block: Bundle.() -> Unit): F {
    val fragment = F::class.java.newInstance()

    val b = Bundle()
    b.block()

    fragment.arguments = b

    return fragment
}