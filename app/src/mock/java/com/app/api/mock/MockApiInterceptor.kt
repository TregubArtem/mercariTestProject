package com.app.api.mock

import com.app.api.ApiPath
import com.app.api.ApiPath.Companion.BASE
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Class that handle mocked responses in testing purposes.
 */
class MockApiInterceptor : Interceptor {

    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val uri = request.url.toString()

        val jsonResponse = when {
            uri.endsWith(ApiPath.TABS)     -> TABS_JSON_RESPONSE
            uri.endsWith("tregub.json")    -> TREGUB_JSON_RESPONSE
            uri.endsWith("artem.json")     -> ARTEM_JSON_RESPONSE
            uri.endsWith("android.json")   -> ANDROID_JSON_RESPONSE
            uri.endsWith("developer.json") -> DEVELOPER_JSON_RESPONSE

            else                           -> throw IllegalStateException("Uri $uri is not supported")
        }
        val contentType = "application/json"

        return chain.proceed(request).newBuilder()
            .code(200)
            .message(jsonResponse)
            .protocol(Protocol.HTTP_2)
            .addHeader("content-type", contentType)
            .body(jsonResponse.toResponseBody(contentType.toMediaTypeOrNull()))
            .build()
    }
}

private const val TABS_JSON_RESPONSE = """
[{
    "name": "Tregub",
    "data": "$BASE/tregub.json"
}, {
    "name": "Artem",
    "data": "$BASE/artem.json"
}, {
    "name": "Android",
    "data": "$BASE/android.json"
}, {
    "name": "Developer",
    "data": "$BASE/developer.json"
}]
"""

private const val TREGUB_JSON_RESPONSE = """
[{
    "id": "tregub0",
    "name": "tregub0",
    "status": "sold_out",
    "num_likes": 0,
    "num_comments": 0,
    "price": 0,
    "photo": "https://upload.wikimedia.org/wikipedia/commons/e/ef/Yuii_Kosin.gif"
}, {
    "id": "tregub1",
    "name": "tregub1",
    "status": "on_sale",
    "num_likes": 1,
    "num_comments": 1,
    "price": 1,
    "photo": "https://upload.wikimedia.org/wikipedia/commons/0/00/Jurij_A_Treguboff5.JPG"
}]    
"""
private const val ARTEM_JSON_RESPONSE = """
[{
    "id": "artem0",
    "name": "artem0",
    "status": "sold_out",
    "num_likes": 0,
    "num_comments": 0,
    "price": 0,
    "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Lesser_Coat_of_Arms_of_Ukraine.svg/330px-Lesser_Coat_of_Arms_of_Ukraine.svg.png"
}, {
    "id": "artem1",
    "name": "artem1",
    "status": "sold_out",
    "num_likes": 1,
    "num_comments": 1,
    "price": 1,
    "photo": "https://upload.wikimedia.org/wikipedia/commons/0/06/Ukraine_KIIS-Regional-division.png"
}, {
    "id": "artem2",
    "name": "artem2",
    "status": "on_sale",
    "num_likes": 2,
    "num_comments": 2,
    "price": 2,
    "photo": "https://upload.wikimedia.org/wikipedia/commons/9/94/Ukraine_Oblast_Saporischja_Rajon_blank.png"
}, {
    "id": "artem3",
    "name": "artem3",
    "status": "sold_out",
    "num_likes": 3,
    "num_comments": 3,
    "price": 3,
    "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Coat_of_arms_of_Zaporizhia_Oblast.svg/922px-Coat_of_arms_of_Zaporizhia_Oblast.svg.png"
}]    
"""

private const val ANDROID_JSON_RESPONSE = """
[{
    "id": "android0",
    "name": "android0",
    "status": "on_sale",
    "num_likes": 0,
    "num_comments": 0,
    "price": 0,
    "photo": "https://images.pexels.com/photos/1495580/pexels-photo-1495580.jpeg"
}, {
    "id": "android1",
    "name": "android1",
    "status": "on_sale",
    "num_likes": 1,
    "num_comments": 1,
    "price": 1,
    "photo": "https://storage.needpix.com/rsynced_images/android.jpg"
}, {
    "id": "android2",
    "name": "android2",
    "status": "on_sale",
    "num_likes": 2,
    "num_comments": 2,
    "price": 2,
    "photo": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGw9WKmzMF29Iug2uNvttP7PWlFQwMLdq40-A1imBpOeHfc1TPeQ"
}, {
    "id": "android3",
    "name": "android3",
    "status": "sold_out",
    "num_likes": 3,
    "num_comments": 3,
    "price": 3,
    "photo": "https://lh3.ggpht.com/O63VXZLSHVtDW4LxuY4wyoKcf0IhIDDVGrwgm8dnGEiAahiEnlAuyP5xas13UCYk7sem1vX2xIknTPy70mDETsFqfw=s0"
}, {
    "id": "android4",
    "name": "android4",
    "status": "sold_out",
    "num_likes": 4,
    "num_comments": 4,
    "price": 4,
    "photo": "https://s3.amazonaws.com/media.eremedia.com/wp-content/uploads/2018/06/07130935/marketing-700x467.jpg"
}, {
    "id": "android5",
    "name": "android5",
    "status": "sold_out",
    "num_likes": 5,
    "num_comments": 5,
    "price": 5,
    "photo": "https://skillvalue.com/jobs/wp-content/uploads/sites/7/2019/06/mobile-developer-android-hybrid-freelance-project.jpg"
}, {
    "id": "android6",
    "name": "android6",
    "status": "sold_out",
    "num_likes": 6,
    "num_comments": 6,
    "price": 6,
    "photo": "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Android_logo_2.svg/512px-Android_logo_2.svg.png"
}, {
    "id": "android7",
    "name": "android7",
    "status": "sold_out",
    "num_likes": 7,
    "num_comments": 7,
    "price": 7,
    "photo": "https://st3.depositphotos.com/1915171/18786/v/1600/depositphotos_187865500-stock-illustration-bank-loans-sign-icon-get.jpg"
}]    
"""
private const val DEVELOPER_JSON_RESPONSE = """
    [{
    "id": "developer0",
    "name": "developer0",
    "status": "sold_out",
    "num_likes": 0,
    "num_comments": 0,
    "price": 0,
    "photo": "https://cdn.pixabay.com/photo/2018/03/15/10/35/website-3227784_960_720.jpg"
}, {
    "id": "developer1",
    "name": "developer1",
    "status": "sold_out",
    "num_likes": 1,
    "num_comments": 1,
    "price": 1,
    "photo": "https://cdn.pixabay.com/photo/2018/03/12/09/19/e-commerce-3219138_960_720.jpg"
}, {
    "id": "developer2",
    "name": "developer2",
    "status": "on_sale",
    "num_likes": 2,
    "num_comments": 2,
    "price": 2,
    "photo": "https://www.lentepubblica.it/wp-content/uploads/2019/04/bonus-pubblicita-2019-codice-tributo.jpeg"
}, {
    "id": "developer3",
    "name": "developer3",
    "status": "on_sale",
    "num_likes": 3,
    "num_comments": 3,
    "price": 3,
    "photo": "https://image.freepik.com/free-vector/young-people-using-technology_23-2147505507.jpg"
}, {
    "id": "developer4",
    "name": "developer4",
    "status": "on_sale",
    "num_likes": 4,
    "num_comments": 4,
    "price": 4,
    "photo": "https://croakey.org/wp-content/uploads/2018/11/corruption.jpg"
}, {
    "id": "developer5",
    "name": "developer5",
    "status": "on_sale",
    "num_likes": 5,
    "num_comments": 5,
    "price": 5,
    "photo": "https://c.pxhere.com/images/46/b4/d2671337be6957fe683f1545bbdb-1456201.jpg!d"
}, {
    "id": "developer6",
    "name": "developer6",
    "status": "on_sale",
    "num_likes": 6,
    "num_comments": 6,
    "price": 6,
    "photo": "https://c.pxhere.com/images/4d/6e/e87b40896ca8d03755264a073486-1571969.jpg!d"
}, {
    "id": "developer7",
    "name": "developer7",
    "status": "on_sale",
    "num_likes": 7,
    "num_comments": 7,
    "price": 7,
    "photo": "https://www.maxpixel.net/static/photo/1x/Business-Technology-Office-Computer-3220141.jpg"
}, {
    "id": "developer8",
    "name": "developer8",
    "status": "sold_out",
    "num_likes": 8,
    "num_comments": 8,
    "price": 8,
    "photo": "https://www.maxpixel.net/static/photo/1x/Idea-Ladder-Creative-Innovation-Reach-Success-3236075.jpg"
}, {
    "id": "developer9",
    "name": "developer9",
    "status": "sold_out",
    "num_likes": 9,
    "num_comments": 9,
    "price": 9,
    "photo": "https://www.maxpixel.net/static/photo/640/Idea-Incidence-Self-Employed-Light-Bulb-3104355.jpg"
}, {
    "id": "developer10",
    "name": "developer10",
    "status": "sold_out",
    "num_likes": 10,
    "num_comments": 10,
    "price": 10,
    "photo": "https://www.maxpixel.net/static/photo/640/Home-Monkey-Mammalia-Animalia-Spring-Nature-Cute-3231956.jpg"
}, {
    "id": "developer11",
    "name": "developer11",
    "status": "sold_out",
    "num_likes": 11,
    "num_comments": 11,
    "price": 11,
    "photo": "https://www.maxpixel.net/static/photo/640/Creative-Earn-Business-Idea-Money-Advertisement-3233190.jpg"
}, {
    "id": "developer12",
    "name": "developer12",
    "status": "on_sale",
    "num_likes": 12,
    "num_comments": 12,
    "price": 12,
    "photo": "https://live.staticflickr.com/2278/2054249744_b0b745a128_z.jpg"
}, {
    "id": "developer13",
    "name": "developer13",
    "status": "on_sale",
    "num_likes": 13,
    "num_comments": 13,
    "price": 13,
    "photo": "https://cdn.pixabay.com/photo/2018/07/13/23/43/mindset-3536805_960_720.jpg"
}, {
    "id": "developer14",
    "name": "developer14",
    "status": "on_sale",
    "num_likes": 14,
    "num_comments": 14,
    "price": 14,
    "photo": "https://cdn.pixabay.com/photo/2018/04/25/00/06/screen-3348489_960_720.jpg"
}, {
    "id": "developer15",
    "name": "developer15",
    "status": "sold_out",
    "num_likes": 15,
    "num_comments": 15,
    "price": 15,
    "photo": "https://cdn.pixabay.com/photo/2017/09/29/17/13/read-2799818_960_720.jpg"
}]
"""
