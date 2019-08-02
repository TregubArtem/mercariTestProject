@file:Suppress("DEPRECATION")

package com.app.utility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkInfo
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.content.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Singleton that responsible for notifying subscribed listeners about current Internet availability state
 */
object InternetAvailableLiveData : LiveData<Boolean>() {

    /** For API < 24 */
    private lateinit var receiver: Receiver
    /** For API >= 24 */
    private lateinit var callback: Callback

    val isAvailable: Boolean get() = value == true

    @Suppress("MemberVisibilityCanBePrivate")
    fun observe(ctx: Context, owner: LifecycleOwner, observer: Observer<in Boolean>) {
        registerReceiver(ctx.applicationContext)
        super.observe(owner, observer)
    }

    fun observe(ctx: Context, owner: LifecycleOwner, block: (result: Boolean?) -> Unit) =
        observe(ctx, owner, Observer(block))

    override fun observe(owner: LifecycleOwner, observer: Observer<in Boolean>) =
        throw NoSuchMethodError("Use method with Context instead")

    private fun registerReceiver(ctx: Context) {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            if (!::callback.isInitialized) {
                val cm = ctx.getSystemService<ConnectivityManager>() ?: return

                postValue(cm.isDefaultNetworkActive && cm.activeNetwork != null)

                callback = Callback(::postValue)
                cm.registerDefaultNetworkCallback(callback)
            }

        } else if (VERSION.SDK_INT < VERSION_CODES.N) {
            if (!::receiver.isInitialized) {
                receiver = Receiver(::postValue)
                ctx.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }
        }
    }

    private class Callback(
        private val onAvailable: (Boolean) -> Unit
                          ) : NetworkCallback() {

        override fun onAvailable(network: Network) =
            onAvailable(true)

        override fun onLost(network: Network) =
            onAvailable(false)

        override fun onUnavailable() =
            onAvailable(false)
    }

    private class Receiver(
        private val onAvailable: (Boolean) -> Unit
                          ) : BroadcastReceiver() {

        override fun onReceive(ctx: Context, intent: Intent) {
            val info = intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
            onAvailable(info?.isConnected == true)
        }
    }
}