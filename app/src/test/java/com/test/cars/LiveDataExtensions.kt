package com.test.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.mockk

@Synchronized
fun <T> LiveData<T>.observeForeverMock(): Observer<T> {
    val observer: Observer<T> = mockk(relaxed = true)
    this.observeForever(observer)
    return observer
}
