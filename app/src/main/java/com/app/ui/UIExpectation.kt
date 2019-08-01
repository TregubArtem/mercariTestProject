package com.app.ui

/** Root interface that describes main properties for UI model */
interface UIExpectation<T> {
    /** Original model, that use to present the data and transferring to next place if needed */
    val origin: T
}