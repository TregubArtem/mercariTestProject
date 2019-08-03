package com.app

import android.app.Application
import com.app.api.model.TimelineItemStatus
import com.app.global.toLog
import com.app.utility.Analytics
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

/** Root class that starts after application launched */
@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fabric.with(this, Crashlytics(), Answers())

        Analytics.startOf(this)

        val r = Random

        val tregubJson = JSONArray()
        (0 until 2).forEach { i ->
            val json = JSONObject()
            json.put("id", "tregub$i")
            json.put("name", "tregub$i")
            json.put("status", if (r.nextBoolean()) TimelineItemStatus.SOLD_OUT else TimelineItemStatus.ON_SALE)
            json.put("num_likes", i)
            json.put("num_comments", i)
            json.put("price", i)
            json.put("photo", null)

            tregubJson.put(json)
        }
        toLog(tregubJson)

        val artemJson = JSONArray()
        (0 until 4).forEach { i ->
            val json = JSONObject()
            json.put("id", "artem$i")
            json.put("name", "artem$i")
            json.put("status", if (r.nextBoolean()) TimelineItemStatus.SOLD_OUT else TimelineItemStatus.ON_SALE)
            json.put("num_likes", i)
            json.put("num_comments", i)
            json.put("price", i)
            json.put("photo", "")

            artemJson.put(json)
        }
        toLog(artemJson)

        val androidJson = JSONArray()
        (0 until 8).forEach { i ->
            val json = JSONObject()
            json.put("id", "android$i")
            json.put("name", "android$i")
            json.put("status", if (r.nextBoolean()) TimelineItemStatus.SOLD_OUT else TimelineItemStatus.ON_SALE)
            json.put("num_likes", i)
            json.put("num_comments", i)
            json.put("price", i)
            json.put("photo", null)

            androidJson.put(json)
        }
        toLog(androidJson)

        val developerJson = JSONArray()
        (0 until 16).forEach { i ->
            val json = JSONObject()
            json.put("id", "developer$i")
            json.put("name", "developer$i")
            json.put("status", if (r.nextBoolean()) TimelineItemStatus.SOLD_OUT else TimelineItemStatus.ON_SALE)
            json.put("num_likes", i)
            json.put("num_comments", i)
            json.put("price", i)
            json.put("photo", "")

            developerJson.put(json)
        }
        toLog(developerJson)
    }
}