package com.app.utility

import android.app.Activity
import android.os.Build
import androidx.collection.SimpleArrayMap
import androidx.fragment.app.Fragment
import com.app.App
import com.app.BuildConfig
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/** Singleton to handle simple analytics */
object Analytics {

    private val frequencyMap = SimpleArrayMap<String, Long>()
    private val format = SimpleDateFormat("MM-dd HH:mm", Locale.ENGLISH)
    private val date = Date()

    fun startOf(target: Any) {
        when (target) {
            is App      -> logCustom("applicationStart") {
                putCustomAttribute("model", Build.MODEL)
                putCustomAttribute("manufacturer", Build.MANUFACTURER)

                putCustomAttribute("sdkCode", Build.VERSION.SDK_INT)
                putCustomAttribute("sdkName", Build.VERSION.CODENAME)

                putCustomAttribute("appFlavor", BuildConfig.FLAVOR)
                putCustomAttribute("appCode", BuildConfig.VERSION_CODE)
                putCustomAttribute("appName", BuildConfig.VERSION_NAME)
                putCustomAttribute("appDebug", BuildConfig.DEBUG.toString())
            }
            is Activity -> logCustom("activityStart") {
                putCustomAttribute("name", target.javaClass.simpleName)
                putCustomAttribute("label", target.toString())
            }
            is Fragment -> logCustom("fragmentStart") {
                putCustomAttribute("name", target.javaClass.simpleName)
                putCustomAttribute("label", target.toString())
            }
        }
    }

    fun orientationChanged(portrait: Boolean) = logCustom("orientationChanged") {
        putCustomAttribute("portrait", portrait.toString())
    }

    fun clickOn(place: String) = logCustom("click") {
        putCustomAttribute("place", place)
    }

    fun internetAccessChange(available: Boolean) = logCustom("internet") {
        putCustomAttribute("available", available.toString())
    }

    private fun getPreviousTimestamp(key: String): Long {
        var timestamp = frequencyMap[key]
        if (timestamp == null) {
            timestamp = System.currentTimeMillis()
            frequencyMap.put(key, timestamp)
        }
        return timestamp
    }

    private fun logCustom(eventName: String, block: CustomEvent.() -> Unit) {
        val event = CustomEvent(eventName)
        event.block()

        val previous = getPreviousTimestamp(eventName)
        val current = System.currentTimeMillis()
        val frequency = TimeUnit.MILLISECONDS.toSeconds(current - previous)

        frequencyMap.put(eventName, current)

        if (frequency > 0)
            event.putCustomAttribute("frequency", frequency)

        date.time = current
        event.putCustomAttribute("timestamp", format.format(date))
        event.putCustomAttribute("timezone", TimeZone.getDefault().displayName)

        Answers.getInstance().logCustom(event)
    }
}