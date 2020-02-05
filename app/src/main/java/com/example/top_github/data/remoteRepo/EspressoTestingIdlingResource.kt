package com.example.top_github.data.remoteRepo

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Idling resource which will tell espresso to wait for another thread to complete network call task
 * We tell espresso about new thread coming into picture by doing increment() when subscribing to api call
 * Similarly we tell espresso to continue on main thread by calling decrement() when api call finishes
 */
object EspressoTestingIdlingResource {
    private const val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }
}