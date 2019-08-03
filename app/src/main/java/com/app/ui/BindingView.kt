package com.app.ui

/** Main interface to illuminate that some view has capabilities to represent some data */
interface BindingView<DATA> {
    /** Used to bind data to view, so the properties be presented to user */
    fun bind(data: DATA)
}