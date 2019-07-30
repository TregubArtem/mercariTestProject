package com.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.view.ITimelineItem
import com.app.view.TimelineItemView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setContentView(R.layout.view_timeline_item)

        val view = TimelineItemView(this)
        view.bind(object : ITimelineItem {
            override val origin: Any = Unit
            override val coverUri: String? =
                "https://seekconz.corewebdna.net.au/web_images/blogs/216/1492/What%20recruiters%20look%20for%20in%20a%20cover%20letter_940x485.jpg"
            override val soldOut: Boolean = true
            override val likesCount: Int = 99
            override val commentsCount: Int = 99999
            override val priceUsd: Int = 1900
        })

        setContentView(view)
        //        findViewById<View>(R.id.btnSell).setOnClickListener { }
    }
}