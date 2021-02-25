package com.example.geofenceapp

import com.example.geofenceapp.network.remote.testAppModule
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

class GeofenceHelperTest : AutoCloseKoinTest() {

    private lateinit var subject: GeofenceHelper

    @Before
    fun setUp() {
        startKoin { modules(testAppModule) }
        subject = GeofenceHelper(subject.applicationContext)
    }

    @Test
    fun `onError returns an error string associated with Exception`() {
        val actual = subject.getErrorString(
            Exception(
            "OutOfMemeoryException"
            )
        )
        val expected = "OutOfMemoryException"
        Assert.assertEquals(expected,actual)

    }
}