package com.test.cars.rule

import io.mockk.unmockkAll
import org.junit.After

interface MockkTest {

    @After
    fun tearDown() {
        unmockkAll()
    }
}