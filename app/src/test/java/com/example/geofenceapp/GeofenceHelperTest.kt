package com.example.geofenceapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.geofenceapp.network.remote.testAppModule
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

class GeofenceHelperTest : AutoCloseKoinTest() {

    private lateinit var subject: GeofenceHelper

    @Before
    fun setUp() {
        val mockContext = mockk<Context>(relaxed = true)
        startKoin { modules(testAppModule) }
        subject = GeofenceHelper(mockContext)
    }

    @Test
    fun `onError returns an error string associated with Exception`() {
        val actual = subject.getErrorString(
            Exception(
                "OutOfMemoryException"
            )
        )
        val expected = "OutOfMemoryException"
        Assert.assertEquals(expected, actual)

    }
}