package com.app.api

/** Annotation used because it looks better and also group similar meaning values at one place */
annotation class ApiPath {
    companion object {
        const val BASE = "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/"
        const val TABS = "master.json"
    }
}