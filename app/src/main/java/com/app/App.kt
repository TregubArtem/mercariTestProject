package com.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric

/** Root class that starts after application launched */
@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics(), Answers())
    }
}